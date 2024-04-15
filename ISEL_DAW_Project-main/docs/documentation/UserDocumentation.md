To make a user action you must use the "/players" URI with an exception to the rankings,login and logout.

###### Creation of a new User ####
Send a POST request to "/players" URI. The body must have the username and password like this:
    creationPlayer(username,password).
If the username is already taken or the passowrd is not strong enough an error response is returned.
The token and identifier of the user is returned to the response.

#### Information about the user ###

Send a GET request to the "/players/{id}". A playerOutput is returned whith the id,username,points and the number of
games played.

### login & logout ####

To login make a POST request to the "/login" URI with the credentials in the body, like the creation of the user.
A cookie with the id and token of the user is put in the response.

To logout make a POST request to the "/logout" URI. This cleans up the cookies with the id and token.


#### ranking ####

To have the rankings, a request to "/ranking". A list with PlayerOuput is returned.

### list all games ###

if a request to the "/players/{id}/games" URI, a list with all the games of that player is returned. Every item
contains the id and the state of said game.