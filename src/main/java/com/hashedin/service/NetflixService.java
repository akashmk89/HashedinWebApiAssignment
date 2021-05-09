package com.hashedin.service;

import com.hashedin.exception.ResourceNotFoundException;
import com.hashedin.model.NetflixShow;
import com.hashedin.repository.NetflixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;


@Service
public class NetflixService {

    @Autowired
    NetflixRepository netflixRepository;

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

    public Collection<NetflixShow> getTVShowsByCount(String type, int count){
        return netflixRepository.getNetflixShowsByCount(type, count);
    }

    public Collection<NetflixShow> getTvShowsByMovieType(String type, String movieType){
        return netflixRepository.getNetflixShowsByMovieType(type,movieType);
    }

    public Collection<NetflixShow> getTvShowsByCountry(String type,String country){
        return netflixRepository.getNetflixShowsByCountry(type,country);
    }

    public Collection<NetflixShow> getTvShowsByStartDateEndDate(String type,Date startDate, Date endDate){
        return netflixRepository.getNetflixShowsBetweenDates(type,startDate,endDate);
    }

    public NetflixShow addNetflixShowIfNotExist(NetflixShow netflixShow) throws Exception{
          if(this.CanAddRecord(netflixShow.getShow_id())) {
              netflixRepository.save(netflixShow);
          }
          return netflixShow ;
    }

    @Transactional
    public void addRecordsToDataBase(List<NetflixShow> netflixShows){
        netflixRepository.saveAll(netflixShows);

    }

    public boolean CanAddRecord(String show_id) throws Exception{
        Optional<NetflixShow> existingNetflixShow = netflixRepository.findById(show_id);
        if(existingNetflixShow.isPresent()){
            throw new Exception("this record already exists");
        }
        return true;
    }

}
