# BattleShip

This was an API developed during the semester of [DAW](https://github.com/isel-leic-daw/s2223i-52d-public), which is a classic and famous board game called battleship. It consists of shooting the enemy's board in order to take down all their boats.    
The documentation can be found can be found [here](#domain-specific) or it can also be found by travelling in the folders.

## Setting up the database
To set up the database locally with [Postgres](https://www.postgresql.org/download/), the User must set up a schema named **dbo** and use the [create](https://github.com/isel-leic-daw/2022-daw-leic52d-2022-daw-leic52d-g09/blob/main/src/main/kotlin/DAW/BattleShip/sql/createSchema.sql)


## Media-type [Siren]

* `class`
* `properties`
* `entities`
* `links`


## Front-End
The frontend part of this application consists of a router with routers for each page of the website. The page folder has the pages that are used.
The pages use the Navigate component to navigate from a page to another.
Polling is used to create a lobby that waits for the other players interactions
For the polling interaction, a special fetch was created, the FetchMultipleRequests

### Setting up Front-End 
It is possible to run the API by using the command `npm start`.
To access the application we should execute the [http://localhost:8081](http://localhost:8080) link

### App
This module creates a router using createBrowserRouter from react-router-dom, defines a nested set of routes with path and element properties, and renders the router with <RouterProvider />.
The routes correspond to the [pages](https://github.com/isel-leic-daw/2022-daw-leic52d-2022-daw-leic52d-g09/tree/main/src/views/react-app/pages) in the game.
The router is wrapped in an AuthnContainer component, which handles authentication, and a GameContext component, which provides state and functions for game setup and play. 
If any errors occur while rendering the routes, an ErrorPage component is rendered instead.

### cookieHandler 
This is a module that exports three functions.
The first function is called doCookie and takes a cookieParams object as a parameter with name, value, expire, and path properties. 
It sets a cookie with those parameters using the document.cookie property.
The second function is called deleteCookie and takes a cookieName string as a parameter. 
The third function is called fetchGetSession and takes a function called onSuccess as a parameter.
It sends a GET request to /api/check_session with credentials: 'include', and parses the response as JSON. 
It then finds the token and id fields in the response object and calls the onSuccess function with these values.

### Lobby
This is a React functional component that represents a lobby page. The component uses a context contextGame to retrieve the board size for the game and a context LoggedInContextCookie to retrieve the user's id.
It makes a request to a fetchCheckLobby function that retrieves the game ID for the lobby, and displays a loading spinner while the request is being processed.
Here we use polling to make several request to check changes that occur.

### Fetch
The fetch function is used to access the backend part of the API. We set two different fetches:
FetchMutipleRequest: React hook that uses the fetch2 function to send an HTTP request upon component mounting.
The LoggedInContextCookie is used to retrieve a token that is passed as an Authorization header to the fetch2 function. 
The hook uses the useState and useEffect hooks to manage the state of the component and trigger the HTTP request. 
The useEffect hook is set to run only once upon component mounting, so that the HTTP request is only sent once.
Fetch: This function sends only one HTTP request to the specified url with the given method and requestBody.
If successful, it sets the response state to the parsed JSON body of the response.
If there's an error, it sets the error state to the error object. 
Finally, the function returns the FetchResponse object with the response and error properties.



## Authors of this Project
* 46536 - Rodrigo Neves
* 47184 - Mafalda Rodrigues
* 47188 - Ines Martins
