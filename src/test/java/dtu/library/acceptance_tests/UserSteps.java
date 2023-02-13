package dtu.library.acceptance_tests;

import dtu.library.app.internal.Address;
import dtu.library.app.LibraryApp;
import dtu.library.app.exceptions.OperationNotAllowedException;
import dtu.library.app.UserInfo;
import io.cucumber.java.en.And;
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
        user_helper.setUser(new UserInfo(cpr, name, email));
        assertEquals(user_helper.getUser().getCPR(), cpr);
        assertEquals(user_helper.getUser().getName(), name);
        assertEquals(user_helper.getUser().getEmail(), email);
    }

    @Given("the user has address street {string}, post code {int}, and city {string}")
    public void theUserHasAddressStreetPostCodeAndCity(String street, Integer post_code, String city) {
        Address new_address = new Address(street, post_code, city);
        user_helper.getUser().setAddress(new_address);
        assertEquals(user_helper.getUser().getAddress(), new_address);
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
        UserInfo user = library_app.getUser(user_helper.getUser().getCPR());

        assertTrue(library_app.userIsRegistered(user_helper.getUser().getCPR()));

        assertEquals(user_helper.getUser().getAddress(), user.getAddress());
        assertEquals(user_helper.getUser().getCPR(), user.getCPR());
        assertEquals(user_helper.getUser().getEmail(), user.getEmail());
        assertEquals(user_helper.getUser().getName(), user.getName());
    }

    @Given("a user is registered with the library")
    public void aUserIsRegisteredWithTheLibrary() {
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
        aUserIsRegisteredWithTheLibrary();
    }

    @And("the user has to pay a fine of {int} DKK")
    public void theUserHasToPayAFineOfDKK(int fine) {
        assertEquals(library_app.getFineForUser(user_helper.getUser()), fine);
    }

    @Then("the user has overdue books")
    public void theUserHasOverdueBooks() {
        assertTrue(library_app.userHasOverdueMedia(user_helper.getUser()));
    }
}
