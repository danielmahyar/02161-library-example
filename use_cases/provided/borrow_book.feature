Feature: Borrow Book
  Description: The user borrows a book
  Actors: User
  Scenario: User borrows book
    Given a book in the library
    And a user is registered with the library
    And the user has not borrowed the book
    When the user borrows the book
    Then the book is borrowed by the user
  Scenario: User borrows book but has already more than 10 books
    Given the user has borrowed 10 books
    And a user is registered with the library
    And a book is in the library
    When the user borrows the book
    Then the book is not borrowed by the user
    And the user gets the error message "Can’t borrow more than 10 books"