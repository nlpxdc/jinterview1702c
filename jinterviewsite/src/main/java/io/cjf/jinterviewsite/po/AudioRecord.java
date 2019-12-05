package io.cjf.jinterviewsite.po;

public class AudioRecord {
    private Integer audioRecordId;

    private Integer interviewId;

    private String title;

    private Integer likes;

    private String url;

    private String content;

    public Integer getAudioRecordId() {
        return audioRecordId;
    }

    public void setAudioRecordId(Integer audioRecordId) {
        this.audioRecordId = audioRecordId;
    }

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}