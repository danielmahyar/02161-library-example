package dtu.library.acceptance_tests;

import dtu.library.app.LibraryApp;
import dtu.library.app.OperationNotAllowedException;
import dtu.library.app.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class UserSteps {

    private final LibraryApp library_app;
    private final ErrorMessageHolder error_message_holder;
    private final UserHelper user_helper;
    private final BookHelper book_helper;

    public UserSteps(LibraryApp app, ErrorMessageHolder errors, UserHelper user_helper, BookHelper book_helper){
        this.library_app = app;
        this.error_message_holder = errors;
        this.user_helper = user_helper;
        this.book_helper = book_helper;
    }

    @Given("there is a user with CPR {string}, name {string}, e-mail {string}")
    public void thereIsAUserWithCPRNameEMail(String cpr, String name, String email) {
        user_helper.createUser(cpr, name, email);
        User new_user = user_helper.getUser();
        assertEquals(cpr, new_user.getCPR());
        assertEquals(name, new_user.getName());
        assertEquals(email, new_user.getEmail());
    }
    @Given("the user has address street {string}, post code {int}, and city {string}")
    public void theUserHasAddressStreetPostCodeAndCity(String address, Integer post_code, String city) {
        user_helper.addFinalInfo(address, post_code, city);
        User new_user = user_helper.getUser();
        new_user.setAddress(address).setPostCode(post_code).setCity(city);
        assertEquals(address, new_user.getAddress());
        assertEquals((int) post_code, new_user.getPostCode());
        assertEquals(city, new_user.getCity());
    }
    @When("the administrator registers the user")
    public void theAdministratorRegistersTheUser() {
        try{
            library_app.registerNewUser(user_helper.getUser());
        } catch (OperationNotAllowedException e){
            error_message_holder.setErrorMessage(e.getMessage());
        }
    }
    @Then("the user is a registered user of the library")
    public void theUserIsARegisteredUserOfTheLibrary() {
        assertTrue(library_app.userIsRegistered(user_helper.getUser().getCPR()));
    }

    @Given("a user is registered with the library")
    public void aUserIsRegisteredWithTheLibrary() {
        user_helper.createUser("123456-7890");
        library_app.adminLogin("adminadmin");
        try {
            library_app.registerNewUser(user_helper.getUser());
        } catch (OperationNotAllowedException e) {
            error_message_holder.setErrorMessage(e.getMessage());
        }
        library_app.adminLogout();
    }

    @When("the administrator registers the user again")
    public void theAdministratorRegistersTheUserAgain() {
        user_helper.createUser("123456-7890");
        try {
            library_app.registerNewUser(user_helper.getUser());
        } catch (OperationNotAllowedException e) {
            error_message_holder.setErrorMessage(e.getMessage());
        }
    }
}
