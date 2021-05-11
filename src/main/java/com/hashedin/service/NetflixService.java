package com.hashedin.service;

import com.hashedin.model.NetflixShow;
import com.hashedin.repository.NetflixRepository;
import com.hashedin.utils.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Service
public class NetflixService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReader.class);
    @Autowired
    NetflixRepository netflixRepository;

    @Autowired
    CsvReader csvReader;
    /**
     * Based on the combination of input function decides which query has to be executed
       and if combination does not match any request then throws the exception
     * @param count
     * @param type
     * @param movieType
     * @param country
     * @param startDate
     * @param endDate
     * @return
     */
    public Collection<NetflixShow> queryDecider(Integer count, String type, String movieType, String country, Date startDate, Date endDate){
    if(count != null && movieType == null && country == null && startDate==null && endDate== null ){
        return  this.getTVShowsByCount(type,count);
    }else if(count == null && movieType != null && country == null && startDate == null && endDate == null){
        return this.getTvShowsByMovieType(type,movieType);
    }else if(count == null && movieType == null && country != null && startDate == null && endDate == null){
        return this.getTvShowsByCountry(type, country);
    }else if(count == null && movieType == null && country == null && startDate!=null && endDate!= null){
        return  this.getTvShowsByStartDateEndDate(type,startDate,endDate);
    }else {
        throw new IllegalArgumentException("cannot process the given combination of input");
    }
    }

    /***
     * query to fetch TV Show by count
     * @param type
     * @param count
     * @return
     */
    public Collection<NetflixShow> getTVShowsByCount(String type, int count){
        return netflixRepository.getNetflixShowsByCount(type, count);
    }

    /**
     * query to fetch TV Show by count
     * @param type
     * @param movieType
     * @return
     */
    public Collection<NetflixShow> getTvShowsByMovieType(String type, String movieType){
        return netflixRepository.getNetflixShowsByMovieType(type,movieType);
    }

    /**
     * query to fetch TV Show by country
     * @param type
     * @param country
     * @return
     */
    public Collection<NetflixShow> getTvShowsByCountry(String type,String country){
        return netflixRepository.getNetflixShowsByCountry(type,country);
    }

    /**
     * query to fetch TV Show between start-date and end-date
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    public Collection<NetflixShow> getTvShowsByStartDateEndDate(String type,Date startDate, Date endDate){
        return netflixRepository.getNetflixShowsBetweenDates(type,startDate,endDate);
    }

    /***
     * Adds a new record to database only if the record with the same show_id does not already exists
     * @param netflixShow
     * @return
     * @throws Exception
     */
    public boolean addNetflixShowIfNotExistToDatabase(NetflixShow netflixShow) throws Exception{
        if(this.CanAddRecord(netflixShow.getShowId())) {
              netflixRepository.save(netflixShow);
                return true;
          }
          return false ;
    }

    public boolean appendToCSV(NetflixShow netflixShow) throws Exception{
        csvReader.insertNetflixMovieToCSV(netflixShow);
        return true;
    }

    public boolean csvOrDatabaseDecider(NetflixShow netflixShow, String destination) throws Exception {
        if (destination.equals("csv")) {
            this.appendToCSV(netflixShow);
            return true;
        } else if (destination.equals("database")){
            return this.addNetflixShowIfNotExistToDatabase(netflixShow);
    }
        return false;
    }

    /***
     * checks for contents of csv file and updates to the database
     * @param netflixShows
     * @throws Exception
     */
    @Transactional
    public void addRecordsToDataBase(List<NetflixShow> netflixShows) throws Exception{
        for (NetflixShow netflixShow:netflixShows) {
            this.addNetflixShowIfNotExistToDatabase(netflixShow);
        }
    }

    /**
     * checks if a record already exists in the database before adding
     * @param show_id
     * @return
     * @throws Exception
     */
    public boolean CanAddRecord(String show_id) throws Exception{
        Optional<NetflixShow> existingNetflixShow = netflixRepository.findById(show_id);
        if(existingNetflixShow.isPresent()){
            return false;
        }
        return true;
    }

}
