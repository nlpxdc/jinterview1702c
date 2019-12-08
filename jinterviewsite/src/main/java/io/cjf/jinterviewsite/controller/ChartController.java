package io.cjf.jinterviewsite.controller;

import io.cjf.jinterviewsite.po.Student;
import io.cjf.jinterviewsite.service.InterviewService;
import io.cjf.jinterviewsite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chart")
@CrossOrigin
public class ChartController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InterviewService interviewService;


    @GetMapping("/student")
    public List<Student> getInfo(){
        List<Student> students = studentService.getStudentInfo();
        return students;
    }


}
