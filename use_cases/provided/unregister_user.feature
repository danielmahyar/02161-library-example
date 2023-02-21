Feature: Unregister user
  Description: The administrator unregisters a user from the library
  Actors: Administrator

  Scenario: Unregister a user
    Given a user is registered with the library
    And that the administrator is logged in
    When the administrator unregisters that user
    Then the user is not registered with the library

  Scenario: Need to be administrator to unregister a user
    Given a user is registered with the library
    And that the administrator is not logged in
    When the administrator unregisters that user
    Then the user is still registered with the library
    And the error message "Administrator required to login" is given

  Scenario: Can't unregister a user that is not registered
    Given a user is not registered with the library
    And that the administrator is logged in
    When the administrator unregisters that user
    Then the error message "User not registered" is given

  Scenario: Can't unregister a user which has borrowed books
    Given a user is registered with the library
    And the user has borrowed a book
    And that the administrator is logged in
    When the administrator unregisters that user
    Then the user is still registered with the library
    And the error message "Can't unregister user: user has still borrowed books/CDs" is given

  Scenario: Can't unregister a user that has fines
    Given a user is registered with the library
    And the user has to pay a fine
    And that the administrator is logged in
    When the administrator unregisters that user
    Then the user is still registered with the library
    And the error message "Can't unregister user: user has still fines to pay" is given
