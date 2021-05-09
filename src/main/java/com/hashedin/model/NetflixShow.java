package com.hashedin.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "netflix_shows")
public class NetflixShow {
    @Id
    @Column(name = "show_id", nullable = false, unique = true)
    private String show_id;

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
    private Date date_added;

    @Column(name = "release_year")
    private int release_year;

    private String rating;

    private String duration;

    @Lob
    @Column(name = "listed_in", length = 3000)
    private String listed_in;

    @Lob
    @Column(name = "description", length = 10000)
    private String description;

}