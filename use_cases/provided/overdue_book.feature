Feature: Overdue book
  Description: Tests which covers all time related events with books
  Actors: User and Administrator
Scenario: Overdue books after 28 days
  Given the user has borrowed a book
  And 29 days have passed
  And the fine for one overdue book is 100 DKK
  Then the user has overdue books
  And the user has to pay a fine of 100 DKK