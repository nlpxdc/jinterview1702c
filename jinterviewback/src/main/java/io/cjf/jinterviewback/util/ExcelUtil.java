package io.cjf.jinterviewback.util;

import io.cjf.jinterviewback.dto.InterviewListDTO;
import io.cjf.jinterviewback.enumeration.InterviewStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExcelUtil {
    @Value("${execl.filename}")
    private String filename;





    public void createExeclHeaderInfo() throws IOException {
        File filecate = new File(filename);
        if(filecate.exists()){
            boolean delete = filecate.delete();

        }
        String [] params={"面试编号","学生姓名","公司名称","面试时间","当前状态"};
        File file = new File(filename);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("xx");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);
        Row row = sheet.createRow(0);
        for(int i=0;i<params.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(params[i]);
            cell.setCellStyle(cellStyle);
        }
        FileOutputStream fos=new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();

    }

    public String appendExeclBodyInfo(List<InterviewListDTO> interviews) throws IOException {
        createExeclHeaderInfo();
        ArrayList<String []> list= (ArrayList<String[]>) interviews.stream().map(interview ->{
            String s1 = FormatDate(interview.getTime());
            String s2 = InterviewStatustoString(interview.getStatus());
            String []s={interview.getInterviewId()+"",interview.getStudentName(),interview.getCompany(),s1,s2};
            return  s;
        }).collect(Collectors.toList());
        File file = new File(filename);
        FileInputStream fis=new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);


        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<list.size();i++){
            Row row = sheet.createRow(lastRowNum+i + 1);
            for(int j=0;j<list.get(i).length;j++){
                Cell cell = row.createCell(j);
                cell.setCellValue(list.get(i)[j]);
            }
        }
        FileOutputStream fos=new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();


        return filename;
    }
    public String InterviewStatustoString(int i){
        InterviewStatus[] values = InterviewStatus.values();
        return  values[i].toString();

    }

    public String FormatDate(Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(time);
        return format;
    }
}
