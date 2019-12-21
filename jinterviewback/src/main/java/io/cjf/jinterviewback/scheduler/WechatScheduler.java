package io.cjf.jinterviewback.scheduler;

import io.cjf.jinterviewback.service.InterviewService;
import io.cjf.jinterviewback.vo.InterviewNotificationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WechatScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InterviewService interviewService;

    @Scheduled(cron = "${interview.notification.interval}")
    public void sendInterviewNotification() {
        logger.info("begin to send interview notification");

        final Date now = new Date();
        final List<InterviewNotificationVO> interviews = interviewService.getInterviewNotification(now);

        for (InterviewNotificationVO interview : interviews) {
            final String openid = interview.getOpenid();
            final String company = interview.getCompany();
            final String address = interview.getAddress();
            final Date interviewTime = interview.getInterviewTime();
            try {
                //todo MQ
                final Long msgId = interviewService.sendInterviewNotification(openid, company, address, interviewTime);
                logger.info("interview notification msgId: {}", msgId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        logger.info("end send interview notification");
    }
}
