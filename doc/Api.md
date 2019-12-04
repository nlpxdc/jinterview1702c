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