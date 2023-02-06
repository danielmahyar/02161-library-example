package dtu.library.acceptance_tests;

import dtu.library.app.User;

public class UserHelper {
    private User user;

    public void createUser(String CPR, String name, String email, String city, String address, int post_code){
        user = new User();
        user.setCPR(CPR).setName(name).setEmail(email).setCity(city).setAddress(address).setPostCode(post_code);
    }

    public void createUser(String CPR, String name, String email){
        user = new User();
        user.setCPR(CPR).setName(name).setEmail(email);
    }

    public void addFinalInfo(String address, int post_code, String city){
        user.setCity(city).setAddress(address).setPostCode(post_code);
    }

    public void createUser(String CPR){
        createUser(CPR, "John Doe", "johndoe@gmail.com", "Lyngby", "Skodsborgvej 190", 2850);
    }

    public User getUser(){
        return user;
    }
}
