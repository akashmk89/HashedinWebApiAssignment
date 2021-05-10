package com.hashedin.model;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
/***
 * Lombok is used to automatically manage getter and setter
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "netflix_shows")
/***
 * A class the maps to the equivalent table in the database
 */
public class NetflixShow {

    @Id
    @Column(name = "show_id", nullable = false, unique = true)
    private String showId;

    private String type;

    private String title;

    @Column(name = "director")
    private String director;

    @Lob
    @Column(name = "cast", length = 3000)
    private String cast;

    @Lob
    @Column(name = "country", length = 2000)
    private String country;

    @Column(name = "date_added")
    private Date dateAdded;

    @Column(name = "release_year")
    private int releaseYear;

    private String rating;

    private String duration;

    @Lob
    @Column(name = "listed_in", length = 3000)
    private String listedIn;

    @Lob
    @Column(name = "description", length = 10000)
    private String description;

}