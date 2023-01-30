Feature: Admin logout
	Description: The administrator logs out from the library system
	Actor: Administrator
	
Scenario: Administrator logs out
	Given that the administrator is logged in
	When the administrator logs out
	Then the administrator is not logged in
