SET
  FOREIGN_KEY_CHECKS = 0;
#------------------------------------------------------------------------
  DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
    `student_id` int(11) NOT NULL auto_increment,
    `nickname` varchar(100) not null,
    `realname` varchar(20),
    `openid` varchar(100) not null,
    `avatar_url` varchar(300),
    `status` tinyint not null,
    `gender` tinyint,
    `mobile` varchar(20),
    `email` varchar(100),
    `mobile_verified` bit not null,
    `email_verified` bit not null,
    PRIMARY KEY (`student_id`),
    unique `idx_openid` (`openid`),
    unique `idx_mobile` (`mobile`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8 auto_increment = 1;
#--------------------------------------------------------------------------
  DROP TABLE IF EXISTS `interview`;
CREATE TABLE `interview` (
    `interview_id` int(11) NOT NULL auto_increment,
    `student_id` int(11), not null,
    `company` varchar(50) not null,
    `address` varchar(200) not null,
    `interview_time` datetime not null,
    `create_time` datetime not null,
    `status` tinyint not null,
    `stars` tinyint not null,
    `note` varchar(500),
    `offer_url` varchar(300),
    PRIMARY KEY (`interview_id`),
    index `idx_student_id` (`student_id`),
    index `idx_interview_time` (`interview_time`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8 auto_increment = 1;
#--------------------------------------------------------------------------
  DROP TABLE IF EXISTS `examination`;
CREATE TABLE `examination` (
    `exam_id` int(11) NOT NULL auto_increment,
    `interview_id` int(11) not null,
    `title` varchar(100),
    `content` varchar(4000),
    `likes` int(11) not null,
    PRIMARY KEY (`exam_id`),
    unique `idx_interview_id` (`interview_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8 auto_increment = 1;
#--------------------------------------------------------------------------
  DROP TABLE IF EXISTS `exam_photo`;
CREATE TABLE `exam_photo` (
    `exam_photo_id` int(11) NOT NULL auto_increment,
    `exam_id` int(11) not null,
    `url` varchar(300) not null,
    PRIMARY KEY (`exam_photo_id`),
    index `idx_exam_id` (`exam_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8 auto_increment = 1;
#--------------------------------------------------------------------------
  DROP TABLE IF EXISTS `audio_record`;
CREATE TABLE `audio_record` (
    `audio_record_id` int(11) NOT NULL auto_increment,
    `interview_id` int(11) not null,
    `title` varchar(100),
    `content` text,
    `likes` int(11) not null,
    `url` varchar(300) not null,
    PRIMARY KEY (`audio_record_id`),
    index `idx_interview_id` (`interview_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8 auto_increment = 1;
#--------------------------------------------------------------------------