package dtu.library.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dtu.library.app.domain.Address;
import dtu.library.app.domain.User;

class TestUserEquals {

    String street = "street";
    int postCode = 567;
    String city = "city";

    String cpr = "123";
    String name = "name";
    String email = "mickey@mouse.dk";

    String mismatch = "****";

    Address addr1;
    Address addr2;

    User user1;
    User user2;

    @BeforeEach
    void setup() {

        addr1 = new Address(street,postCode,city);
        addr2 = new Address(street,postCode,city);

        user1 = new User(cpr,name,email);
        user1.setAddress(addr1);

    }

    @Test
    void testUserEqualsSuccessWithoutAddress() {
        user1.setAddress(null);
        user2 = new User(cpr,name,email);
        assertEquals(user1,user2);
    }

    @Test
    void testUserEqualsSuccessWithAddress() {
        user2 = new User(cpr,name,email);
        user2.setAddress(addr2);
        assertEquals(user1,user2);
    }

    @Test
    void testUserEqualsNoSuccessCprMismatch() {
        user2 = new User(mismatch,name,email);
        user2.setAddress(addr2);
        assertNotEquals(user1,user2);

    }

    @Test
    void testUserEqualsNoSuccessNameMismatch() {
        user2 = new User(cpr,mismatch,email);
        user2.setAddress(addr2);
        assertNotEquals(user1,user2);

    }

    @Test
    void testUserEqualsNoSuccessEmailMismatch() {
        user2 = new User(cpr,name,mismatch);
        user2.setAddress(addr2);
        assertNotEquals(user1,user2);

    }

    @Test
    void testUserEqualsNoSuccessAddressMismatch() {
        user2 = new User(cpr,name,email);
        assertNotEquals(user1,user2);
    }


}
