package com.hashedin.utils;
import com.hashedin.model.NetflixShow;
import com.opencsv.CSVParser;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
//import org.c
import java.io.*;
import java.text.ParseException;
import java.util.*;
@Component
/**
 * reads record from csv file and adds them to the application memory
 */
public class CsvReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReader.class);
    public List<NetflixShow> readCSVAndGetRecords() throws IOException, ParseException {
        //File file = ResourceUtils.getFile("classpath:netflix_titles2.csv");
        //File file = new FileReader("/netflix_titles2.csv");
        DateFormat dateFormat = new DateFormat();
        String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        List<NetflixShow> netflixShows = new ArrayList<NetflixShow>();
        int count = 0;
        String dataRow = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/netflix_titles.csv"));
        /**
         * to skip the header column of the csv
         */
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

    public NetflixShow insertNetflixMovieToCSV(NetflixShow netflixShow) throws Exception {
       LOGGER.info("inside insert to csv");
        File file = ResourceUtils.getFile("classpath:netflix_titles2.csv");
        FileWriter csvWriter = new FileWriter(file,true);
        csvWriter.append(netflixShow.getShowId()==null ? "" : netflixShow.getShowId());
        csvWriter.append(",");
        csvWriter.append(netflixShow.getType()==null ? "" : netflixShow.getType());
        csvWriter.append(",");
        csvWriter.append(netflixShow.getTitle()==null ? "" : netflixShow.getTitle());
        csvWriter.append(",");
        csvWriter.append(netflixShow.getDirector()==null ? "" : "\""+netflixShow.getDirector()+"\"");
        csvWriter.append(",");
        csvWriter.append(netflixShow.getCast()==null ? "" : "\""+netflixShow.getCast()+"\"");
        csvWriter.append(",");
        csvWriter.append(netflixShow.getCountry()==null ? "" : "\""+netflixShow.getCountry()+"\"");
        csvWriter.append(",");
        csvWriter.append(netflixShow.getDateAdded()==null ? "" : netflixShow.getDateAdded().toString());
        csvWriter.append(",");
        csvWriter.append(String.valueOf(netflixShow.getReleaseYear()));
        csvWriter.append(",");
        csvWriter.append(netflixShow.getRating()==null ? "" : netflixShow.getRating());
        csvWriter.append(",");
        csvWriter.append(netflixShow.getDuration()==null ? "" : netflixShow.getDuration());
        csvWriter.append(",");
        csvWriter.append(netflixShow.getListedIn()==null ? "" : "\""+netflixShow.getListedIn()+"\"");
        csvWriter.append(",");
        csvWriter.append(netflixShow.getDescription()==null ? " " : "\""+netflixShow.getDescription()+"\"");
        csvWriter.append("\n");
        csvWriter.close();
        return netflixShow;
    }
    }

