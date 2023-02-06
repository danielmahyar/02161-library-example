Feature: Register user
  Description: The administrator registers a user of the library
  Actors: Administrator

  Scenario: Register a user
    Given that the administrator is logged in
    And there is a user with CPR "020563-4886", name "Helena M. Bertelsen", e-mail "HelenaMBertelsen@rhyta.com "
    And the user has address street "SlÃ¥enhaven 50", post code 4560, and city "Vig"
    When the administrator registers the user
    Then the user is a registered user of the library

  Scenario: Register a user when not the administrator
    Given that the administrator is not logged in
    And there is a user with CPR "020563-4886", name "Helena M. Bertelsen", e-mail "HelenaMBertelsen@rhyta.com "
    And the user has address street "SlÃ¥enhaven 50", post code 4560, and city "Vig"
    When the administrator registers the user
    Then the error message "Administrator login required" is given

  Scenario: Register a user that is already registered
    Given a user is registered with the library
    And that the administrator is logged in
    When the administrator registers the user again
    Then the error message "User is already registered" is given

  # Scenario: Register a new user with identical information but different CPR
  #  Given that the administrator is logged in
  #  And there is a user with CPR "020563-4886", name "Helena M. Bertelsen", e-mail "HelenaMBertelsen@rhyta.com "
  #  And the user has address street "SlÃ¥enhaven 50", post code 4560, and city "Vig"
  #  When the administrator registers the user
  #  And there is a user with CPR "020563-4836", name "Helena M. Bertelsen", e-mail "HelenaMBertelsen@rhyta.com "
  #  And the user has address street "SlÃ¥enhaven 50", post code 4560, and city "Vig"
  #  Then the error message "Information is the same only different CPR" is given
