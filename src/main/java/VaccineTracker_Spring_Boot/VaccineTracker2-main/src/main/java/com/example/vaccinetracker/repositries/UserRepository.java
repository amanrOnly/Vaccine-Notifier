package com.example.vaccinetracker.repositries;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.vaccinetracker.entities.User;


public interface UserRepository extends CrudRepository<User, Integer> {

}