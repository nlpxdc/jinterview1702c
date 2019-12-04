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