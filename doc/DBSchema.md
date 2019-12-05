# Student
| 字段  | 类型  | 约束  |  说明 |
|---|---|---|---|
| student_id  | int(11)  | 主键 自增 | Id  |
| nickname | varchar(100)  |  | 昵称  |
| realname  | varchar(20)  |  | 真实姓名  |
| openid  | varchar(100)  | 唯一索引 非空 | 微信openid  |
| avatar_url  | varchar(300)  |   | 头像url  |
| status  | tinyint  |  非空，默认0 | 状态（0未激活，1激活）  |
| gender  | tinyint  |   | 性别（0保密，1男，2女）  |
| mobile  | varchar(20)  |  唯一索引 | 手机号  |
| email  | varchar(100)  |   | 邮箱  |
| mobile_verified  | bit  |  非空，默认false | 手机验证  |
| email_verified  | bit  | 非空，默认false  | 邮箱验证  |

# Interview
| 字段  | 类型  | 约束  |  说明 |
|---|---|---|---|
| interview_id  | int(11)  | 主键 自增 | Id  |
| student_id | int(11)  | 外键 索引 | 学生id  |
| company  | varchar(50)  | 非空 | 公司名  |
| address  | varchar(200)  | 非空 | 公司地址  |
| interview_time  | datetime  | 非空 索引  | 面试时间  |
| create_time  | datetime  |  非空 | 创建时间  |
| status  | tinyint  | 非空 默认0  | 状态（待面试0，Offer1，等通知2，凉凉3，取消4）  |
| stars  | tinyint  |   | 评星  |
| note  | varchar(500)  |   | 备注  |
| offer_url  | varchar(300)  |   | offer url  |

# Examination
| 字段  | 类型  | 约束  |  说明 |
|---|---|---|---|
| exam_id  | int(11)  | 主键 自增 | Id  |
| interview_id | int(11)  | 非空 外键 索引 | 面试Id  |
| title  | varchar(100)  |  | 标题  |
| content  | varchar(4000)  |  | OCR内容  |
| likes  | int(11)  | 非空 默认0  | 点赞数  |

# ExamPhoto
| 字段  | 类型  | 约束  |  说明 |
|---|---|---|---|
| exam_photo_id  | int(11)  | 主键 自增 | Id  |
| exam_id | int(11)  | 外键 索引 | 笔试题Id  |
| url  | varchar(300)  | 非空 | 图片Url  |

# AudioRecord
| 字段  | 类型  | 约束  |  说明 |
|---|---|---|---|
| audio_record_id  | int(11)  | 主键 自增 | Id  |
| interview_id | int(11)  | 外键 索引 | 面试Id  |
| title  | varchar(100)  |  | 标题  |
| content  | text  |  | 语音识别内容  |
| likes  | int(11)  | 非空 默认0  | 点赞数  |
| url  | varchar(300)  | 非空 | 图片Url  |

