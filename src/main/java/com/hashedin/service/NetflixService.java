package com.hashedin.service;

import com.hashedin.model.NetflixShow;
import com.hashedin.repository.NetflixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class NetflixService {

    @Autowired
    NetflixRepository netflixRepository;

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

    public boolean addNetflixShowIfNotExist(NetflixShow netflixShow) throws Exception{
          if(this.CanAddRecord(netflixShow.getShowId())) {
              netflixRepository.save(netflixShow);
                return true;
          }
          return false ;
    }

    /***
     * checks for contents of csv file and updates to the database
     * @param netflixShows
     * @throws Exception
     */
    @Transactional
    public void addRecordsToDataBase(List<NetflixShow> netflixShows) throws Exception{
        for (NetflixShow netflixShow:netflixShows) {
            this.addNetflixShowIfNotExist(netflixShow);
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
