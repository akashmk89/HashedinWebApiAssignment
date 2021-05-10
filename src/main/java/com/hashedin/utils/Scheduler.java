package com.hashedin.utils;

import com.hashedin.model.NetflixShow;
import com.hashedin.service.NetflixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    @Autowired
    NetflixService netflixService;

    @Autowired
    CsvReader csvReader;

    /***
     * Scheduler function runs every 30 minutes and Synchronizes CSV file with database
     * @throws IOException
     * @throws ParseException
     * @throws Exception
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateDatabaseRecords() throws IOException, ParseException, Exception {
        LOGGER.info("CSV to Database scheduled function ran at" + LocalDateTime.now());
        List<NetflixShow> netflixShows = csvReader.readCSVAndGetRecords();
        netflixService.addRecordsToDataBase(netflixShows);
    }
}
