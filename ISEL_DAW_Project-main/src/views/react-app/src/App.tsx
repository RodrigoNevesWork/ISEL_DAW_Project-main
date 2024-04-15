import React from 'react';
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import Home from "../pages/Home";

import Login from "../pages/Auth/Login";
import {AuthnContainer} from "../pages/Auth/Authn";
import AboutUsPage from "../pages/AboutUs";
import Signup from "../pages/Auth/SignUp";
import {GameContext} from "../pages/Game/ChooseGameSize";
import {Lobby} from "../pages/Lobbies/Lobby";
import StartGameTemplate from "../pages/Game/ChooseGameSize";
import PlacingBoatsPage from "../pages/Game/PlacingBoats";
import WaitingForPlayerPage from "../pages/Lobbies/WaitingForPlayerPlacing";
import PlayerStats from "../pages/PlayerStatistics";
import {RedirectToGame} from "../pages/Lobbies/RedirectToGame";
import MyTurn from "../pages/Game/OwnTurn";
import OpponentTurn from "../pages/Game/OpponentTurn";
import {GamePage} from "../pages/Game/Game";
import ErrorPage from "../utils/Errors";


const router = createBrowserRouter([
    {
        "path": "/",
        "element": <AuthnContainer><GameContext><Outlet /></GameContext></AuthnContainer>,//outlet é trocado pelo componente do path
        "errorElement":<ErrorPage/>,
        "children": [
            {
                "path": "/",
                "element": <Home/>
            },
                {
                        "path": "/ranking",
                        "element": <PlayerStats/>,
                },
                {
                        "path": "/about_us",
                        "element": <AboutUsPage />,
                },
                {
                        "path": "/login",
                        "element": <Login/>,
                },
                {
                         "path": "/players",
                         "element": <Signup/>,
                },
                {
                         "path": "/game/:id/own-turn",
                         "element": <MyTurn/>,
                },
                {
                        "path": "/game-play/:id",
                        "element": <GamePage/>,
                },
               /* {
                       "path": "/game/:id/opponent-turn",
                       "element":<OpponentTurn/>,
                },*/
                {
                       "path": "/games",
                       "element": <StartGameTemplate/>,
                },
                {
                       "path": "/games/lobby",
                       "element":  <Lobby/>,
                },
                {
                      "path": "/game/redirect-to-game/:id",
                      "element":  <RedirectToGame/>,
                },
                {
                     "path":"/games/placing-boats/:id/:board",
                     "element": <PlacingBoatsPage/>,
                },
                {
                    "path":"/game/:id",
                    "element":<WaitingForPlayerPage/>,
                 },
                {
                   "path":"/game/redirect-lobby",
                   "element": <WaitingForPlayerPage/>,
                },
                {
                   "path":"/game/redirect-player/:id",
                   "element": <WaitingForPlayerPage/>,
                }
                ]
    }
])

function WelcomeToBattleShip(){
    return (
        <RouterProvider router={router} />
    )
}
export default WelcomeToBattleShip;