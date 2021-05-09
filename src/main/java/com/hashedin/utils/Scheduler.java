package com.hashedin.utils;

import com.hashedin.model.NetflixShow;
import com.hashedin.service.NetflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.IIOException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    NetflixService netflixService;

    @Autowired
    CsvReader csvReader;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateDatabaseRecords() throws IOException, ParseException {
        System.out.println("Scheduler function ran at" + LocalDateTime.now());
        List<NetflixShow> netflixShows = csvReader.readCSVAndGetRecords();
        netflixService.addRecordsToDataBase(netflixShows);
    }
}
