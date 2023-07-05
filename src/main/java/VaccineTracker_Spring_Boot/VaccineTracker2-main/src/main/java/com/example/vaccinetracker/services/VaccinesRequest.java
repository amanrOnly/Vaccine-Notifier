package com.example.vaccinetracker.services;
import com.example.vaccinetracker.entities.Center;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.vaccinetracker.entities.VaccineUser;
import com.example.vaccinetracker.repositries.VaccineUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VaccinesRequest {
    //@Autowired
   // private static VaccineUserRepository vaccineUserRepository;


    public static void getPeople(Iterable<VaccineUser> people) throws IOException, InterruptedException {

        //Iterable<VaccineUser> people = vaccineUserRepository.findAll();

        for(VaccineUser vu : people){
            getVaccines(vu.getPincode(),vu.getEmail());
        }


    }
    public static void getVaccines(String pincode,String emailId) throws InterruptedException, IOException {
        VaccineServiceHttpRequest vaccineService = new VaccineServiceHttpRequest();
        //Scanner sc = new Scanner(System.in);
        //String pincode = "110031";

       // while (true) {
           List<Center> centers = vaccineService.isAvailable(pincode);

           if (centers.size()>0){
               System.out.println("preparing to send message ...");
               String message = " Vaccine is available\n" + centers.stream().map(Center::getAddress /* Method reference */ ).collect(Collectors.joining("\n"));
               String subject = "COWIN : Availability for " + pincode;
               String to = emailId;
               String from = "tid413816@gmail.com";
               sendEmail(message,subject,to,from);
               System.out.println("Vaccine for "+pincode+" is Available!");
           }

           else System.out.println("Vaccine for "+pincode+" is not Available!");

           //calls after X minutes
           //Thread.sleep(6000);
   //    }

    }

    private static void sendEmail(String message, String subject, String to, String from) {

        //Variable for gmail
        String host="smtp.gmail.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES "+ properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Step 1: to get the session object..
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(".com", "");
            }
        });

        session.setDebug(true);

        //Step 2 : compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);

        try {

            //from email
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);


            //adding text to message
            m.setText(message);

            //send

            //Step 3 : send the message using Transport class
            Transport.send(m);

            System.out.println("...................Sent success...................");


        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
