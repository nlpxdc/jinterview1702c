package io.cjf.jinterviewsite.po;

public class ExamPhoto {
    private Integer examPhotoId;

    private Integer examId;

    private String url;

    public Integer getExamPhotoId() {
        return examPhotoId;
    }

    public void setExamPhotoId(Integer examPhotoId) {
        this.examPhotoId = examPhotoId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}