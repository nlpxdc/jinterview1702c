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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/examphoto")
public class ExamPhotoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashSet md5Set = new HashSet();

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ExamPhotoService examPhotoService;

    @Transactional
    @PostMapping("/upload")
    public List<String> upload(@RequestPart("examphotos") List<MultipartFile> photos,
                               @RequestParam Integer interviewId) throws IOException {

        final Examination examination = new Examination();
        examination.setInterviewId(interviewId);
        examination.setLikes(0);

        final Examination exam = examinationService.getExamByInterviewId(interviewId);
        Integer examId;
        if (exam == null){
            examId = examinationService.createExamination(examination);
        }else {
            examId = exam.getExamId();
        }


        final ArrayList<String> urls = new ArrayList<>();

        for (MultipartFile photo : photos) {
            String name = photo.getOriginalFilename();
            String[] split = name.split("\\.");
            String ext = split[split.length - 1];
            byte[] data = photo.getBytes();
            String uuid = UUID.randomUUID().toString();
            String filename = String.format("Idcardphoto/%s.%s", uuid, ext);
            urls.add(filename);

            String md5HexStr = DigestUtils.md5DigestAsHex(data);

            if (!md5Set.contains(md5HexStr)) {
                try (FileOutputStream out = new FileOutputStream(filename)) {
                    out.write(data);
                }
                md5Set.add(md5HexStr);

                final ExamPhoto examPhoto = new ExamPhoto();
                examPhoto.setExamId(examId);
                examPhoto.setUrl(filename);
                final Integer examPhotoId = examPhotoService.createExamPhoto(examPhoto);
            }

        }

        return urls;
    }
}
