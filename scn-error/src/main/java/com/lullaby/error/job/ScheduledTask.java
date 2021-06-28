package com.lullaby.error.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTask {

    static int count = 0;

    @Scheduled(cron = "*/10 * * * * ?")
    public void record() {
        count++;
        //log.info("record: " + count);
    }
}
