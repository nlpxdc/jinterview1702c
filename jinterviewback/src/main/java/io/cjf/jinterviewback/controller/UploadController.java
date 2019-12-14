package io.cjf.jinterviewback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jinterviewback.dto.FileDTO;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {


    @PostMapping("/photos")
    public String base64(@RequestBody FileDTO base64) throws IOException {
        System.out.println(base64.getId());
        /*// 读取图片字节数组
        byte[] bytes = file.getBytes();
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        String base64 = encoder.encode(Objects.requireNonNull(bytes));*/

        String content = base64.getContent();
        String base = content.substring(23);

        Map<String,String> param  = new HashMap<String,String>();
        Map<String,String> header  = new HashMap<String,String>();

        param.put("access_token","24.90477a6234d89b34cbc2b4963bc6cf70.2592000.1578731415.282335-17990718");
        param.put("image",base);
        param.put("id_card_side","front");

        header.put("Content-Type","application/x-www-form-urlencoded");

        String s = doPost("https://aip.baidubce.com/rest/2.0/ocr/v1/idcard", param, header);
        String[] split = s.split("\"words\": \"");
        String[] s1 = split[3].split("\"}");
        String name = s1[0];

        //修改信息


        return s;
    }

    /**
     * @Auther: gengshuai@cetiti.com
     * @Description: application/x-www-form-urlencoded编码方式带参数和请求头的POST请求
     * @Param: [url, param, header]
     * @Return: java.lang.String
     * @Create: 2018-9-27 14:54
     */
    public static String doPost(String url, Map<String, String> param, Map<String, String> header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            //添加请求头信息
            if (header != null) {
                for (String key : header.keySet()) {
                    httpPost.setHeader(key, header.get(key).toString());
                }
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }


}
