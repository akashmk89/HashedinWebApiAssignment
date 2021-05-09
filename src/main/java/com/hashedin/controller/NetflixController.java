package com.hashedin.controller;

import com.hashedin.model.NetflixShow;
import com.hashedin.service.NetflixService;
import com.hashedin.utils.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/netflix")
@CrossOrigin(origins = "http://127.0.0.1")
public class NetflixController {

    @Autowired
    NetflixService netflixService;

    // Returns one of the four queries based on the input
    @GetMapping("/tv-shows")
    public ResponseEntity getTvShows(@RequestParam(required = false) Integer count,
                                     @RequestParam(required = false) String movieType,
                                     @RequestParam(required = false) String country,
                                     @RequestParam(required = false) Date startDate,
                                     @RequestParam(required = false) Date endDate){
        long startTime = System.currentTimeMillis();
        String type = "TV Show";
        try {
            List<NetflixShow> shows =(List<NetflixShow>) netflixService.queryDecider(count,type,movieType,country,startDate,endDate);
            long endTime = System.currentTimeMillis();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("X-TIME-TO-EXECUTE",Long.toString (endTime-startTime) + "ms");
            return ResponseEntity.ok().headers(responseHeaders).body(shows);
        }   catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addShowToDatabase(@RequestBody NetflixShow netflixShow) throws Exception{
        long startTime = System.currentTimeMillis();
        try {
            netflixService.addNetflixShowIfNotExist(netflixShow);
            long endTime = System.currentTimeMillis();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("X-TIME-TO-EXECUTE",Long.toString (endTime-startTime) + "ms");
            return ResponseEntity.ok().headers(responseHeaders).body("Record added successfully");
        } catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/write-from-csv")
    public void readCsvAndAddToDatabase() throws IOException, ParseException {
     CsvReader csvReader = new CsvReader();
     List<NetflixShow> netflixShows =  csvReader.readCSVAndGetRecords();
     netflixService.addRecordsToDataBase(netflixShows);
    }

}
