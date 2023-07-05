package com.example.vaccinetracker.controller;

import com.example.vaccinetracker.repositries.VaccineUserRepository;
import com.example.vaccinetracker.services.VaccinesRequest;
import com.example.vaccinetracker.repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.vaccinetracker.entities.User;
import com.example.vaccinetracker.entities.VaccineUser;

import java.io.IOException;

@RestController
@RequestMapping(path = "/demo")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VaccineUserRepository vaccineUserRepository;

    @Autowired
    private VaccinesRequest vaccinesRequest;

    @Scheduled(fixedRate = 60000)
    public String pong() throws IOException, InterruptedException {
        VaccinesRequest.getPeople(vaccineUserRepository.findAll());
        return "";
    }
//    @GetMapping(path="/ping")
//    public String pong() throws IOException, InterruptedException {
//        VaccinesRequest.getPeople(vaccineUserRepository.findAll());
//        return "";
//    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email,@RequestParam String pincode) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        VaccineUser n = new VaccineUser();
        n.setName(name);
        n.setEmail(email);
        n.setPincode(pincode);
        vaccineUserRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<VaccineUser> getAllUsers() {
        // This returns a JSON or XML with the users
        return vaccineUserRepository.findAll();
    }

}
