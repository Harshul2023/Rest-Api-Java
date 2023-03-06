package com.example.RestApi2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class ApiController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/hy")
    public String greet(){
        return "HELLO GUYS";
    }
    @GetMapping(value = "/read")
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    @GetMapping(value = "read/{id}")
    public Optional<User> getUserWithId(@PathVariable long id) {
        if(userRepo.findById(id).isPresent())
        {
            return userRepo.findById(id);
        }
        return Optional.empty();
    }
    @PostMapping(value = "/create")
    public String saveUser(@RequestBody User user) {
        if(userRepo.findById(user.getId()).isPresent())
            return "Duplicate entry";
        else {
            userRepo.save(user);
        }
        return "Saved....";
    }
    @PutMapping(value = "update/{id}")
    public String updateUser(@PathVariable long id,@RequestBody User user)
    {
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setOccupation(user.getOccupation());
        updatedUser.setAge(user.getAge());
        userRepo.save(updatedUser);
        return "updated...";
    }
    @DeleteMapping(value = "delete/{id}")
    public String deleteUser(@PathVariable long id){
        if(userRepo.existsById(id)){
            User deleteUser = userRepo.findById(id).get();
            userRepo.delete(deleteUser);
            return "user with id " + id + " deleted";
        }
        return "User Does Not Exist";

    }

}
