package io.cjf.jinterviewback.controller;

import io.cjf.jinterviewback.po.Interview;
import io.cjf.jinterviewback.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private InterviewService interviewService;


    @GetMapping("/interviewCount")
    public Map<String, Long> interviewCount(){


        List<Interview> interviews = interviewService.getInterviewCount();

        Map<String, Long> interCount = interviews.stream().collect(
                Collectors.groupingBy(Interview::getRealName, Collectors.counting()));
        return interCount;
    }




    //保存到本地t图片
    @PostMapping("/saveword")
    public void pp(@RequestBody String img) throws IOException, DocumentException {
        File f =new File("pdffile.jpg");

        if(f.exists()){
            f.delete();
        }

        String decode = URLDecoder.decode(img, "UTF-8");
        String s = decode.replaceAll(" ", "+");
        String substring1 = s.substring(22);
        // Base64解码
        Base64 base64 = new Base64();
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] b = decoder.decodeBuffer(substring1);

        OutputStream out = new FileOutputStream(f);
        out.write(b);
        out.flush();
        out.close();
        saveword();;
        savepdf();
    }
    //    保存本地word
public void saveword() throws IOException, DocumentException {
    File file = new File("report.doc");
    if(file.exists()){
        file.delete();
    }
    Document document = new Document(PageSize.A4);

    RtfWriter2.getInstance(document, new FileOutputStream(file));
    document.open();

    Paragraph title = new Paragraph("学生面试数统计报告");
    //
    // 设置标题格式对齐方式

    title.setAlignment(Element.ALIGN_CENTER);

    // title.setFont(titleFont);

    document.add(title);

    com.lowagie.text.Image instance = com.lowagie.text.Image.getInstance("pdffile.jpg");
    instance.setAbsolutePosition(0, 0);
    instance.scalePercent(50);
    document.add(instance);
    document.close();

}

//    保存本地pdf

    @GetMapping("/savepdf")

    public void savepdf() throws IOException, DocumentException {
        File file = new File("report.pdf");
        if(file.exists()){
            file.delete();
        }
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20); //new一个pdf文档

        PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();//打开文档

        BaseFont bfChinese = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        doc.add(new Paragraph("sss 学生面试数统计报告"));
        com.lowagie.text.Image instance = com.lowagie.text.Image.getInstance("pdffile.jpg");
        instance.scalePercent(90);
        doc.add(instance);
        doc.close();

    }

    //下载word

    @GetMapping(value = "/downword",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downword() throws IOException {
        File file = new File("report.doc");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = StreamUtils.copyToByteArray(fileInputStream);



        return bytes;
    }

    //下载pdf

    @GetMapping(value = "/downpdf",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downpdf() throws IOException {
        File file = new File("report.pdf");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = StreamUtils.copyToByteArray(fileInputStream);




        return bytes;
    }



}
