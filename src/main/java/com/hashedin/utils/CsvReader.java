package com.hashedin.utils;

import com.hashedin.model.NetflixShow;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
@Component
public class CsvReader {
    public List<NetflixShow> readCSVAndGetRecords() throws IOException, ParseException {
        DateFormat dateFormat = new DateFormat();
        String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        List<NetflixShow> netflixShows = new ArrayList<NetflixShow>();
        int count = 0;
        String dataRow = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\netflix_titles.csv"));
        while ((dataRow = bufferedReader.readLine()) != null) {
            if (count == 0) {
                count++;
                continue;
            }
            String[] token = dataRow.split(delimiter);
            NetflixShow show = new NetflixShow(token[0], token[1], token[2], token[3],
                    token[4].replaceAll("\"", ""), token[5], dateFormat.getDate(token[6])
                    , Integer.parseInt(token[7]), token[8], token[9], token[10], token[11].replaceAll("\"", ""));
            netflixShows.add(show);
        }
        System.out.println("There are total of " + netflixShows.size() + " records in Netflix database");

        return netflixShows;
    }
}
