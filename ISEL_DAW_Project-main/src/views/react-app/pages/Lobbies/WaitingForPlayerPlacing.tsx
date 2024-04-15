import React, {useEffect, useState} from "react";
import {contextGame} from "../Game/ChooseGameSize";
import {Navigate, useParams} from 'react-router-dom';
import {getStateGame} from "../../services/gameServices";
import NavBar from "../Layout";
import {LoggedInContext} from "../Auth/Authn";
import PacmanLoader from "react-spinners/PacmanLoader"
import ClockLoader from "react-spinners/ClockLoader"


function WaitingForPlayerPage(){
    const waitTime = 500
    const params = useParams()
    const [request, setRequest] = useState(true)
    const getGameState = getStateGame(params.id)
    const ctx = React.useContext(contextGame)
    const ctxLog = React.useContext(LoggedInContext)

    console.log("HELLO",getGameState)

    useEffect(()=>{
        const interval = setInterval(_ => setRequest(!request),waitTime)
        return () => {
            clearInterval(interval)
        };
    },[request,setRequest])

    if(!getGameState.content ){
           return(
               <div>
                   <NavBar />
                   <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                       <PacmanLoader color={'#abe4e8'} loading={true} size={50} />
                       <p><h1>..Waiting for player to place their boats...</h1></p>
                   </div>
               </div>
           )
       }

    if(getGameState.content.properties.state === "NOT_BEGUN"){
           return(
               <div>
                   <NavBar/>
                   <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                       <PacmanLoader color={'#abe4e8'} loading={true} size={50} />
                       <p><h1>..Waiting for player to place their boats...</h1></p>
                   </div>
               </div>
           )
    }else{
           return(
               <div>
                   <p>
                       ... GAME IS ABOUT TO START ...
                   </p>
                   <Navigate to={`/game-play/${ctx.gameId}`} replace={true}/>
               </div>
           )
       }
}

export default WaitingForPlayerPage;