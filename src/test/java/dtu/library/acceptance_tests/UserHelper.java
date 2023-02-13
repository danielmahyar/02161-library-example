package dtu.library.acceptance_tests;

import dtu.library.app.internal.Address;
import dtu.library.app.UserInfo;

public class UserHelper {
    private UserInfo user;

    public UserInfo getUser(){
        if(user == null){
            user = exampleUser();
        }
        return user;
    }

    public UserInfo exampleUser(){
        UserInfo user = new UserInfo("233278-9809", "Daniel C. Mahyar", "s224797@gmail.com");
        Address user_address = new Address("Skodsborgvej 190, st. 872", 2850, "Nærum");
        user.setAddress(user_address);
        return user;
    }
    public void setUser(UserInfo user_info){
        user = user_info;
    }
}
