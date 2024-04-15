For any related action about a game you must use the uri starting with "/games". A user must be authenticated to make a game related action by putting the respective token in the request.

######## Create a new Game #########
To start a game a POST request is made to "/games" and a response with the class LobbyResult is returned. If the value of this class is null it means
that the player has been put in the lobby, otherwise an id of the new Game is returned and a redirect to the respective URI.


######## Game Setup #########
After a game creation you have to set up the ships. It's needed 3 ships per type of ships. A POST request is made to ""/games/{id}/placing_phase" with a list of the ships in the body. To define a ship you have to do the follow:
    ShipInput( name_of_the_ship, direction, position_of_the_head). The acceptable name for the ships are:"CARRIER","BATTLESHIP","CRUISER","SUBMARINE","DESTROYER". There are no distinction between uppercase and downcase.
The directions can be Horizontal or Vertical, the letters H and V are also acceptable.


######## Game Play ##########

In playing phase of the game to make a play you make a POST request to "games/{id}" with the position in the body by using the PositionInput class that receives a String as a parameter. A GameOutput is returned with the current
state of the Game. This contains the player and opponent boards, the state of the game, and the size of the board. To quit the game make a POST request to "/games{id}/quit". You can't make a play if the game hasn't begun or
has already ended.

To receives information about a game a GET request to the "games/{id}" URI must be made. A GameOutput is returned. If the user making a request is not part of the game, an error response is returned.

You can also only request to see the see your own board or the opponent board. You only have to make a GET request to respectivily, "/games/{id}/ownfleet" or "/games/{id}/opponent".
