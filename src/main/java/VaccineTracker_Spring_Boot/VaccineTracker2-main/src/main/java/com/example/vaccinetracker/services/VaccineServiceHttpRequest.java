package com.example.vaccinetracker.services;

import com.example.vaccinetracker.entities.Center;
import com.example.vaccinetracker.entities.Root;
import com.example.vaccinetracker.entities.Session;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
//
//import java.io.File;
//
//import java.util.Properties;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.com.example.vaccinetracker.entities.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;

public class VaccineServiceHttpRequest {


    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<Center> isAvailable(String pin) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now =   LocalDateTime.now();

        //Enter Pincode
        HttpGet request = new HttpGet("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pin+"&date=" + dtf.format(now));

        request.addHeader("authority", "cdn-api.co-vin.in");
        request.addHeader("sec-ch-ua", "\"Chromium\";v=\"90\", \"Opera GX\";v=\"76\", \";Not A Brand\";v=\"99\"");
        request.addHeader("accept", "application/json, text/plain, */*");
        request.addHeader("sec-ch-ua-mobile", "?0");
        request.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36 OPR/76.0.4017.227");
        request.addHeader("origin", "https://www.cowin.gov.in");
        request.addHeader("sec-fetch-mode", "cors");
        request.addHeader("sec-fetch-dest", "empty");
        request.addHeader("referer", "https://www.cowin.gov.in/");
        request.addHeader("accept-language", "en-US,en;q=0.9");
        request.addHeader("if-none-match", "W/\"6496-4Ib5g1axFmqM7yUm3x2gvuPdYpw\"");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            List<Center> centers = new ArrayList<>();

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                Root root = objectMapper.readValue(result, Root.class);

                for (int i = 0; i < root.centers.size(); i++) {
                    List<Session> sessions = root.centers.get(i).getSessions();
                    for (int j = 0; j < sessions.size(); j++) {
                        Session session = sessions.get(j);
                        if (session.min_age_limit == 18 && session.available_capacity > 0) {
                            centers.add(root.centers.get(i));
                        }
                    }
                }
  //         System.out.println(root.toString());
                return centers;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}


