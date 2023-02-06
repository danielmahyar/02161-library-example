Feature: Return book
  Description: A user can return a borrowed book
  Actors: User
  Scenario: User returns book
    Given a book in the library
    And a user is registered with the library
    And the book is not borrowed by the user
    When the user borrows the book
    And the user returns the book
    Then the user has not borrowed the book
  Scenario: User returns a book that user has not borrowed
    Given a book in the library
    And a user is registered with the library
    And the user has not borrowed the book
    When the user returns the book
    Then the user gets the error message "Book is not borrowed by the user"
#  Scenario: User has borrowed 10 books, returns one and borrows one again
#    Given the user has borrowed 9 books
#    And a user is registered with the library
#    And a book is in the library
#    When the user borrows the book
#    And the user returns the book