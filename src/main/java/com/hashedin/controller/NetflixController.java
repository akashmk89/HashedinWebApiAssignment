package com.hashedin.controller;
import com.hashedin.model.NetflixShow;
import com.hashedin.service.NetflixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1")
/***
 * Controller class for web-api
 */
public class NetflixController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetflixController.class);
    @Autowired
    NetflixService netflixService;

    /***
     * If the combination of Input does not meet and query criteria then throws a exception
     * one end point that handles multiple requests based on the user input and gives the time taken to execute the query
     * @param count fetches n TV Show records mentioned by count
     * @param movieType fetches all TV Show mentioned by movieType
     * @param country fetches all TV Show mentioned by country
     * @param startDate fetches all TV Show added between start date and end date
     * @param endDate
     * @return Tv Shows as demanded by query parameters
     */
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
            LOGGER.info("time taken to execute query =" +(endTime-startTime) +"ms");
            return ResponseEntity.ok().headers(responseHeaders).body(shows);
        }   catch (Exception exception){
            LOGGER.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * To add a json of type NetflixShow to the database if it does not exist and gives the time taken to execute the query
//     * @param netflixShow
     * @return success or message saying "record already exists"
     * @throws Exception
     */
    @PostMapping("/add")
    public ResponseEntity<String> addShowToDatabase(@RequestParam String destination, @RequestBody NetflixShow netflixShow) throws Exception{
        long startTime = System.currentTimeMillis();
        try {
           boolean isRecordAdded = netflixService.csvOrDatabaseDecider(netflixShow,destination);
            long endTime = System.currentTimeMillis();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("X-TIME-TO-EXECUTE",Long.toString (endTime-startTime) + "ms");
            LOGGER.info("time taken to execute query =" +(endTime-startTime) +"ms");
            return ResponseEntity.ok().headers(responseHeaders).body( isRecordAdded?"Record added successfully":"This record already exists");
        } catch (Exception exception){
            LOGGER.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
