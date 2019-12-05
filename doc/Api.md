## 1.1 获取学生自己的个人信息

URL: /student/getBasicInfo  
Method：GET  

ResponseBody:  
```json
{
    "realname": "cjf",
    "mobile": "13423456789",
    "email": "34345656@qq.com",
    "avatarUrl": "http://xxx.com/xxx.jpg"
}

```

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| realname   | String   | 真实姓名    |
| mobile   | String   | 手机号    |
| email   | String   | 邮箱    |
| avatarUrl   | String   | 头像Url    |


## 2.1 查询面试列表

URL: /interview/search?keyword={keyword}&onlyme={onlyme}&time={time}  
Method：GET  

ResponseBody:  
```json
{
    "company": "华为",
    "student": "cjf",
    "time": 1575448390345,
    "status": 1
}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| keyword   | String   | 关键字    |
| onlyme   | Boolean   | 只看自己    |
| time   | Long   | 时间戳    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| company   | String   | 公司名    |
| student   | String   | 面试学生    |
| time   | Long   | 面试时间    |
| status   | byte   | 面试状态    |

## 2.2 创建面试

URL: /interview/create  
Method：POST  

RequestBody:  
```json
{
    "company": "华为",
    "address": "上海徐家汇",
    "time": 1575448390345
}

```

ResponseBody:  
```json
123456

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| company   | String   | 公司名    |
| address   | String   | 公司地址    |
| time   | Long   | 面试时间戳    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
|    | Integer   | 面试Id    |


## 2.3 修改面试

URL: /interview/update  
Method：POST  

RequestBody:  
```json
{
    "interviewId": 123456,
    "time": 1575448390345,
    "status": 2,
    "stars": 4,
    "note":"公司急缺人"
}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 面试Id    |
| time   | Long   | 面试时间戳    |
| status   | Byte   | 面试状态    |
| stars   | Byte   | 打星评分    |
| note   | String   | 备注    |

## 2.3 查看面试详情

URL: /interview/getById?interviewId={interviewId}  
Method：GET  

ResponseBody:  
```json
{
    "interviewId": 123456,
    "company": "华为",
    "address": "上海徐家汇",
    "time": 1575448390345,
    "stars": 4,
    "note": "公司急缺人",
    "offerUrl": "http://xxx.com/xxx.jpg",
    "examphotoUrls": [
        "http://xxx.com/xxx1.jpg",
        "http://xxx.com/xxx2.jpg"
    ],
    "audiorecordUrl": "http://xxx.com/xxx.mp3"
}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 面试Id    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 面试Id    |
| company   | String   | 公司名    |
| address   | String   | 公司地址    |
| time   | Long   | 面试时间戳    |
| stars   | Byte   | 评星    |
| note   | String   | 备注    |
| offerUrl   | String   | offer url    |
| examphotoUrls   | Array(String)   | 笔试题Urls    |
| audiorecordUrl   | String   | 录音Url    |

## 3.1 笔试题上传

URL: /examphoto/upload?interviewId={interviewId}  
Method：POST  
Request Content-Type: multipart/formdata  
RequestParam: audiorecords  

ResponseBody:  
```json
[
    "http://xxx.com/xxx1.jpg",
    "http://xxx.com/xxx2.jpg"
]

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 面试Id    |
| audiorecords   | String   | 上传文件key    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
|    | Array(String)   | 上传图片后Urls    |

## 4.1 查询试题列表

URL: /exam/search?keyword={keyword}&time={time}  
Method：GET  

ResponseBody:  
```json
{
    "company": "腾讯",
    "student": "李维",
    "time": 1575448390345,
    "likes":50
}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| keyword   | String   | 关键字    |
| time   | Long   | 时间戳    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| company   | String   | 公司名    |
| student   | String   | 面试学生    |
| time   | Long   | 面试时间    |
| likes   | int   | 点赞    |

## 4.2 查看试题详情

URL: /exam/getExamById?examId={examId}  
Method：GET  

ResponseBody:  
```json
{
    "examId": 123456,
    "content":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
    "student": "李维",
    "time": 1575448390345,
    "likes":50

}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 面试Id    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| examId   | Integer   | 试题Id    |
| content   | String   | 试题内容    |
| student   | String   | 面试学生    |
| time   | Long   | 面试时间    |
| likes   | int   | 点赞    |

## 5.1 查询录音列表

URL: /audiorecord/search?keyword={keyword}&time={time}  
Method：GET  

ResponseBody:  
```json
{
    "company": "百度",
    "student": "wsh",
}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| keyword   | String   | 关键字    |
| time   | Long   | 时间戳    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| company   | String   | 公司名    |
| student   | String   | 面试学生    |

## 5.2 查看录音

URL: /audiorecord/getById?audiorecordId={audiorecordId}  
Method：GET  

ResponseBody:  
```json
{
    "audiorecordId": 2,
    "title": "SpringMVC运行原理",
    "content": "用户发送请求...",
    "likes": 111,
    "audiorecordUrl": "http://xxx.com/xxx.mp3"
}

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 面试Id    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| audiorecordId   | Integer   | 录音Id    |
| title   | String   | 录音标题    |
| content   | String   | 语音识别内容    |
| likes   | Long   | 点赞数    |
| audiorecordUrl   | String   | 录音Url    |


## 5.3 录音上传

URL: /audioRecord/upload?interviewId={interviewId}  
Method：POST  
Request Content-Type: multipart/formdata  
RequestParam: audiorecords  

ResponseBody:  
```json
[
    "http://xxx.com/xxx1.mp3"
]

```

Request Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
| interviewId   | Integer   | 录音Id    |
| audiorecords   | String   | 上传文件key    |

Response Field  

| 字段     |     类型 |   描述   | 
| :--------------: | :--------:| :------: |
|    | Array(String)   | 上传音频后Urls    |