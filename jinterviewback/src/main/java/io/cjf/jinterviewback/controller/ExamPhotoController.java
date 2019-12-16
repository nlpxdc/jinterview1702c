package io.cjf.jinterviewback.controller;

import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.ExamPhoto;
import io.cjf.jinterviewback.po.Examination;
import io.cjf.jinterviewback.service.ExamPhotoService;
import io.cjf.jinterviewback.service.ExaminationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/examphoto")
public class ExamPhotoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ExamPhotoService examPhotoService;

    @Value("${static.resource.store.directory}")
    private String staticResourceStoreDirectory;

    @Value("${exam.photo.directory}")
    private String examPhotoDirectory;

    @Value("${exam.photo.url.prefix}")
    private String examPhotoUrlPrefix;

    @Transactional
    @PostMapping("/upload")
    public void upload(@RequestPart("examphotos") List<MultipartFile> photos,
                       @RequestParam Integer interviewId) throws IOException, ClientException {
        if (photos.size() == 0) {
            throw new ClientException(ClientExceptionConstant.PHOTO_EMPTY_ERRCODE, ClientExceptionConstant.PHOTO_EMPTY_ERRMSG);
        }
        for (MultipartFile photo : photos) {
            final String contentType = photo.getContentType();
            if (!contentType.equals(MediaType.IMAGE_JPEG_VALUE)) {
                throw new ClientException(ClientExceptionConstant.NOT_JPEG_FORMAT_ERRCODE, ClientExceptionConstant.NOT_JPEG_FORMAT_ERRMSG);
            }
        }

        final Examination exam = examinationService.getExamByInterviewId(interviewId);
        Integer examId;
        if (exam == null) {
            final Examination examination = new Examination();
            examination.setInterviewId(interviewId);
            examination.setLikes(0);
            examId = examinationService.createExamination(examination);
        } else {
            examId = exam.getExamId();
        }

        final LinkedList<String> filenames = new LinkedList<>();
        final LinkedList<String> storeFilenames = new LinkedList<>();
        for (MultipartFile photo : photos) {

//            byte[] data = photo.getBytes();
            //todo redraw photo with fixed size, 看短边，最大为720p
            final BufferedImage originImage = ImageIO.read(photo.getInputStream());
            final int width = originImage.getWidth();
            final int height = originImage.getHeight();
            BufferedImage newImage = originImage;
            if (width < height) {
                if (width > 720) {
                    double newHeight = 720.0 * height / width;
                    newImage = new BufferedImage(720, (int) newHeight, originImage.getType());
                    final Graphics2D graphics = newImage.createGraphics();
                    graphics.drawImage(originImage, 0, 0, 720, (int) newHeight, null);
                    graphics.dispose();
                }
            } else {
                if (height > 720) {
                    double newWidth = 720.0 * width / height;
                    newImage = new BufferedImage((int) newWidth, 720, originImage.getType());
                    final Graphics2D graphics = newImage.createGraphics();
                    graphics.drawImage(originImage, 0, 0, (int) newWidth, 720, null);
                    graphics.dispose();
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newImage, "jpg", baos);
            byte[] data = baos.toByteArray();


            String md5HexStr = DigestUtils.md5DigestAsHex(data);
            String filename = String.format("%s.%s", md5HexStr, "jpg");
            filenames.add(filename);

            final List<ExamPhoto> examPhotos = examPhotoService.getByFilename(filename);
            if (examPhotos.size() == 0 && !storeFilenames.contains(filename)) {
                String storePathname = String.format("%s%s/%s", staticResourceStoreDirectory, examPhotoDirectory, filename);
                try (FileOutputStream out = new FileOutputStream(storePathname)) {
                    out.write(data);
                }
                storeFilenames.add(filename);
            }
        }

        final Integer count = examPhotoService.deleteByExamId(examId);

        for (String filename : filenames) {
            final ExamPhoto examPhoto = new ExamPhoto();
            examPhoto.setExamId(examId);
            examPhoto.setUrl(filename);
            final Integer examPhotoId = examPhotoService.createExamPhoto(examPhoto);
        }

    }
}
