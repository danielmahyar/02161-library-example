Feature: Overdue book
  Description: Tests which covers all time related events with books
  Actors: User and Administrator
Scenario: Overdue book after 29 days
  Given a user is registered with the library
  And the user has borrowed a book
  Then the user has to pay no fine
  And the fine for one overdue book is 100 DKK
  Given 29 days have passed
  Then the user has overdue books
  And the user has to pay a fine of 100 DKK
Scenario: 10 books overdue books
  Given a user is registered with the library
  And the user has borrowed 9 books
  And the fine for one overdue book is 100 DKK
  And 29 days have passed
  Then the user has overdue books
  And the user has to pay a fine of 900 DKK
# TODO: Implement feature that checks multiple books, where some are overdue and other aren't