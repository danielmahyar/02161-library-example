Feature: Send reminder
  Description: Send an e-mail to all users with overdue books
  Actors: Administrator

  Scenario: Send reminder e-mail
    Given that the administrator is logged in
    And there is a user with one overdue book
    When the administrator sends a reminder e-mail
    Then then the user receives an email with subject "Overdue book(s)/CD(s)" and text "You have 1 overdue book(s)/CD(s)"

  Scenario: The administrator is not logged in
    Given that the administrator is logged in
    And there is a user with one overdue book
    And the administrator logs out
    When the administrator sends a reminder e-mail
    Then the error message "Administrator required to login" is given