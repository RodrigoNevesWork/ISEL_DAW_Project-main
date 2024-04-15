import React, {useEffect, useState} from "react";
import {fetchCheckLobby} from "../../services/gameServices";
import {contextGame} from "../Game/ChooseGameSize";
import {Navigate} from "react-router-dom";
import {LoggedInContextCookie} from "../Auth/Authn";
import NavBar from "../Layout";
import PacmanLoader from "react-spinners/PacmanLoader";




export function Lobby() {

    const ctx = React.useContext(contextGame)
    const ctxLog = React.useContext(LoggedInContextCookie).loggedInState.id

    if(!ctx){
        return(
        <div>
            <p>...</p>
        </div>
        )
    }

    const fetchCreateLobby = fetchCheckLobby({player: ctxLog})
    const waitTime = 1000

    const [madeRequest, setMadeRequest] = useState(true)
    console.log("LOBBY -> ",fetchCreateLobby)

    useEffect(() => {
        const interval = setInterval(_ => setMadeRequest(!madeRequest), waitTime);
        return () => {
            clearInterval(interval);
        };
    }, [madeRequest, setMadeRequest]);

    if (!fetchCreateLobby.content) {
        return (
            <div>
                <NavBar />
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                    <PacmanLoader color={'#3e73b6'} loading={true} size={50} />
                    <p><h1>..Waiting for player...</h1></p>
                </div>
            </div>
        )
    }

    const gameId = fetchCreateLobby.content.properties.id
    if (!gameId) {
        return (
            <div>
                <NavBar/>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                    <PacmanLoader color={'#3e73b6'} loading={true} size={50} />
                    <p><h1>..Waiting for player...</h1></p>
                </div>
            </div>
        )
    }
    if (gameId) {
        ctx.gameId = gameId
        return (
            <div>
                <Navigate to={"/games/placing-boats/"+gameId+"/"+ctx.boardSize} replace={true}/>
            </div>
        );
    }
}
