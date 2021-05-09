package com.hashedin.demo;

import com.hashedin.model.NetflixShow;
import com.hashedin.repository.NetflixRepository;
import com.hashedin.service.NetflixService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
//import org.mockito.Mockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class NetflixControllerTest {
    @Autowired
    private NetflixService netflixService;

    @Mock
    private NetflixRepository netflixRepository;

    @Test
    public void getNetflixTvShowsByCount(){
        Mockito.when(netflixRepository.getNetflixShowsByCount("TV Show", 2))
                .thenReturn(Stream.of(
                new NetflixShow("s1", "TV Show", "title", "director",
                        "cast", "India", new Date(),2017 , "Tv-Ma",
                        "4 Seasons", "horror", "description"),
                new NetflixShow("s1", "TV Show", "title", "director",
                        "cast", "India", new Date(),2017 , "Tv-Ma",
                        "4 Seasons", "horror", "description"))
                        .collect(Collectors.toList()));
                assertEquals(2,netflixService.getTVShowsByCount("TV Show",2).size());
    }
}
