package com.hashedin.repository;
import com.hashedin.model.NetflixShow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Date;

@Repository
public interface NetflixRepository extends CrudRepository<NetflixShow, String> {
    /***
     * Native query that fetches only those many records as per query
     * @param type show Type
     * @param count number of record to be fetched
     * @return
     */

    @Query(value = "SELECT * FROM netflix_shows shows WHERE shows.type = :type ORDER BY show_id ASC LIMIT :count", nativeQuery = true)
    public Collection<NetflixShow> getNetflixShowsByCount( @Param("type") String type, @Param("count") int count);

    @Query(value = "SELECT * FROM netflix_shows shows WHERE shows.type = :type and shows.listed_in LIKE %:movieType%", nativeQuery = true)
    public Collection<NetflixShow> getNetflixShowsByMovieType( @Param("type") String type, @Param("movieType") String movieType);

    @Query(value = "SELECT * FROM netflix_shows shows WHERE shows.type = :type and shows.country LIKE %:country%", nativeQuery = true)
    public Collection<NetflixShow> getNetflixShowsByCountry( @Param("type") String type, @Param("country") String country);

    @Query(value = "SELECT * FROM netflix_shows shows WHERE shows.type = :type and shows.date_added BETWEEN :startDate AND :endDate", nativeQuery = true)
    public Collection<NetflixShow> getNetflixShowsBetweenDates(@Param("type") String type, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
