Feature: Admin login
	Description: The administrator logs into the library system and also logs out
	Actor: Administrator
	
Scenario: Administrator can login
	Given that the administrator is not logged in
	And the password is "adminadmin"
	Then the administrator login succeeds
	And the adminstrator is logged in

Scenario: Administrator has the wrong password
	Given that the administrator is not logged in
	And the password is "wrong password"
	Then the administrator login fails
	And the administrator is not logged in
