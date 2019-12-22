package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.client.AMapService;
import io.cjf.jinterviewback.client.BaiduAIService;
import io.cjf.jinterviewback.constant.ClientExceptionConstant;
import io.cjf.jinterviewback.dto.InterviewCreateDTO;
import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.dto.InterviewUpdateDTO;
import io.cjf.jinterviewback.exception.ClientException;
import io.cjf.jinterviewback.po.*;
import io.cjf.jinterviewback.pojo.Position;
import io.cjf.jinterviewback.service.*;
import io.cjf.jinterviewback.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InterviewService interviewService;


    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ExamPhotoService examPhotoService;

    @Autowired
    private AudioRecordService audioRecordService;

    Logger logger = LoggerFactory.getLogger(InterviewController.class);

    @Autowired
    private BaiduAIService baiduAIService;

    @Autowired
    private AMapService aMapService;

    @Autowired
    private ExcelUtil excelUtil;
    @GetMapping("/getById")
    public JSONObject getById(@RequestParam Integer interviewId, @RequestAttribute("studentId") Integer currentStudentId) throws ClientException {
        JSONObject interviewJson = new JSONObject();
        Interview interview=interviewService.getById(interviewId);
        if (interview == null){
            throw new ClientException(ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRCODE, ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRMSG);
        }
        interviewJson.put("interviewId",interviewId);
        final Integer studentId = interview.getStudentId();
        final Student student = studentService.getByStudentId(studentId);
        interviewJson.put("studentId", studentId);
        String studentName = student.getRealname() != null ? student.getRealname() : student.getNickname();
        interviewJson.put("studentName", studentName);
        interviewJson.put("self", studentId == currentStudentId);

        interviewJson.put("company",interview.getCompany());
        interviewJson.put("address",interview.getAddress());

        interviewJson.put("time",interview.getInterviewTime().getTime());
        interviewJson.put("stars",interview.getStars());
        interviewJson.put("status",interview.getStatus());
        interviewJson.put("note",interview.getNote());
        interviewJson.put("offerUrl",interview.getOfferUrl());
        Examination exam=examinationService.getExamByInterviewId(interview.getInterviewId());
        Integer examId = exam != null ? exam.getExamId() : null;
        interviewJson.put("examId", examId);
        AudioRecord audioRecord=audioRecordService.getAudioRecordByInterviewId(interview.getInterviewId());
        String audioRecordUrl = audioRecord != null ? audioRecord.getUrl() : "";
        interviewJson.put("audiorecordUrl",audioRecordUrl);
        return interviewJson;
    }

    @GetMapping("/search")
    public List<InterviewListDTO> search(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false, defaultValue = "false") Boolean onlyme,
                                         @RequestParam(required = false) Long time,
                                         @RequestAttribute Integer studentId){

        Integer studentIdDB = (onlyme ? studentId : null);
        Date date = (time == null ? null : new Date(time));

        List<InterviewListDTO> interviews = interviewService.search(keyword, studentIdDB, date);

        return interviews;
    }
    @GetMapping(value = "/downloadinterview",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downloadinterview() throws IOException {

        List<InterviewListDTO> interviewListDTOS = interviewService.search(null, null, null);
        String filename = excelUtil.appendExeclBodyInfo(interviewListDTOS);
        FileInputStream fis = new FileInputStream(filename);
        byte[] bytes = StreamUtils.copyToByteArray(fis);

        return bytes;
    }

    @PostMapping("/create")
    public Integer create(@RequestBody InterviewCreateDTO interviewCreateDTO, @RequestAttribute Integer studentId){
        final String company = interviewCreateDTO.getCompany();
        final String address = interviewCreateDTO.getAddress();
        final Long time = interviewCreateDTO.getTime();
        final Date date = new Date(time);
        final Integer interviewId = interviewService.createInterview(company, address, date, studentId);
        return interviewId;
    }

    @PostMapping("/update")
    public void update(@RequestBody InterviewUpdateDTO interviewUpdateDTO) throws ClientException {
        final Integer interviewId = interviewUpdateDTO.getInterviewId();
        Interview interview = interviewService.getById(interviewId);
        if (interview == null){
            throw new ClientException(ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRCODE, ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRMSG);
        }
        interview.setNote(interviewUpdateDTO.getNote());
        interview.setStars(interviewUpdateDTO.getStars());
        interview.setStatus(interviewUpdateDTO.getStatus());
        interview.setInterviewTime(new Date(interviewUpdateDTO.getTime()));

        interviewService.updateById(interview);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Integer interviewId, @RequestAttribute Integer studentId) throws ClientException {
        final Interview interview = interviewService.getById(interviewId);
        if (interview == null){
            throw new ClientException(ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRCODE, ClientExceptionConstant.INTERVIEW_NOT_EXIST_ERRMSG);
        }
        final Integer studentIdDB = interview.getStudentId();
        if (studentId != studentIdDB){
            throw new ClientException(ClientExceptionConstant.NOT_YOURSELF_INTERVIEW_ERRCODE, ClientExceptionConstant.NOT_YOURSELF_INTERVIEW_ERRMSG);
        }
        interviewService.deleteById(interviewId);
        //todo delete exam, examphoto, audiorecord same time ?
    }

    @GetMapping("/getPositionByAddress")
    public Position getPositionByAddress(@RequestParam String address){
        final Position position = aMapService.getPosition(address);
        return position;
    }


//    @PostMapping("/distinguish")
//    public String distinguish(@RequestBody String[] examIndex){
//
//        String path="D:/cjf/bai/jinterview1702c/jinterviewfront/exam/photo/";
//        Integer exam_id= Integer.parseInt(examIndex[0]);//获取数组带过来的ID，修改时用
//        Examination examination = examinationService.selectByPrimaryKey(exam_id);//根据ID取对象
//        String content = examination.getContent();//识别内容
//        //数据库里的识别内容是否为空
//        if (content == null){
//            //数组第一个数值为ID 跳过
//            for (int i = 1; i < examIndex.length; i++) {
//
//                String addressUrl = examIndex[i];
//                String[] split = addressUrl.split("/");
//                addressUrl = path + split[split.length-1];
//                String image = null;
//                InputStream in = null;
//                try {
//                    File file = new File(addressUrl);
//                    in = new FileInputStream(file);
//                    byte[] bytes=new byte[(int)file.length()];
//                    in.read(bytes);
//                    image = Base64.getEncoder().encodeToString(bytes);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (in != null) {
//                        try {
//                            in.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                logger.info(image);
//                JSONObject distinguish = baiduAIService.distinguish(image, "application/x-www-form-urlencoded");
//                //分割保存
//                JSONArray words_result = distinguish.getJSONArray("words_result");
//
//                for (int j = 0; j < words_result.size(); j++) {
//                    JSONObject words = words_result.getJSONObject(j);
//                    String s = words.toString();
//                    String con = s.substring(10, s.length() - 2);
//                    content += con;
//                }
//            }
//            logger.info(content);
//            //保存到数据库
//            examination.setExamId(exam_id);
//            examination.setContent(content);
//            examinationService.updateByPrimaryKeySelective(examination);
//        }
//        return content;
//    }



}
