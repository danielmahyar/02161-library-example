Feature: Search books 
	Description: A user (either an ordinary user or the administrator) searches for books by 
				 providing a substring of either the title, author, or signature field
	Actors: user

Background: The library has a set of books
	Given that the administrator is logged in
	And these books are contained in the library
		| Extreme Programming | Kent Beck | Beck99 |
		| Test Driven Development | Kent Beck | Beck02 |
		| Lean Software Development | Mary Poppendieck and Tom Poppendieck | Pop07 |
		| Cucumber for Java | Seb Rose | Rose11 |
	And the administrator logs out

Scenario: Searching for a substring of the signature
	Given that the administrator is not logged in
	When the user searches for the text "99"
	Then the book with signature "Beck99" is found

Scenario: Searching for a substring of the title
	Given that the administrator is not logged in
	When the user searches for the text "Extreme"
	Then the book with signature "Beck99" is found

Scenario: Searching for a substring of the author
	Given that the administrator is not logged in
	When the user searches for the text "Seb"
	Then the book with signature "Rose11" is found

Scenario: Searching also works when the administrator is logged in
	Given that the administrator is logged in
	When the user searches for the text "Seb"
	Then the book with signature "Rose11" is found

Scenario: No books match the criteria
	When the user searches for the text "Ian"
	Then no books are found

Scenario: Find more than one book
	When the user searches for the text "Beck"
	Then the books with signatures "Beck99" and "Beck02" are found
