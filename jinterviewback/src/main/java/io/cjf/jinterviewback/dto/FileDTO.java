package io.cjf.jinterviewback.dto;

public class FileDTO {
    //前台传来图片base64码
    private String content;

    //前台传来修改人的ID
    private int id;

//    private String address;
//    private String time;
//    private String name;
//    private String logid;
//    private String logid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
