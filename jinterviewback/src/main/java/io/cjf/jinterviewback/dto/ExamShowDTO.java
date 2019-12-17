package io.cjf.jinterviewback.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cjf.jinterviewback.po.ExamPhoto;
import io.cjf.jinterviewback.po.Examination;

import java.util.List;

public class ExamShowDTO extends Examination {

    @JsonProperty("interview")
    private InterviewShowDTO interviewShowDTO;

    @JsonIgnore
    private List<ExamPhoto> examPhotos;

    private List<String> examPhotoUrls;

    public InterviewShowDTO getInterviewShowDTO() {
        return interviewShowDTO;
    }

    public void setInterviewShowDTO(InterviewShowDTO interviewShowDTO) {
        this.interviewShowDTO = interviewShowDTO;
    }

    public List<ExamPhoto> getExamPhotos() {
        return examPhotos;
    }

    public void setExamPhotos(List<ExamPhoto> examPhotos) {
        this.examPhotos = examPhotos;
    }

    public List<String> getExamPhotoUrls() {
        return examPhotoUrls;
    }

    public void setExamPhotoUrls(List<String> examPhotoUrls) {
        this.examPhotoUrls = examPhotoUrls;
    }
}
