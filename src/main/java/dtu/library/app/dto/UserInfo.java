package dtu.library.app.dto;

import dtu.library.app.domain.Address;
import dtu.library.app.domain.User;

public class UserInfo {
    private String CPR, name, email;
    private Address address;

    public UserInfo(String CPR, String name, String email) {
        this.CPR = CPR;
        this.name = name;
        this.email = email;
    }

    public UserInfo(User user) {
        this.CPR = user.getCPR();
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = user.getAddress();
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCPR() {
        return CPR;
    }

    public Address getAddress() {
        return address;
    }
}
