
import * as React from "react"
import {getGameSimple, getStateGame} from "../../services/gameServices";
import {Link, Navigate, useParams} from "react-router-dom";
import {DomainGameState} from "../../utils/DomainTypes";
import {useEffect, useState} from "react";
import {delay} from "../../utils/useFetch";
import NavBar from "../Layout";


export function GamePage(){
    const gameId = useParams().id
    const getGameState = getStateGame(gameId).content

    const [request,setResquest] = useState<boolean>(true)
    const timeFetch = 1000

    useEffect(() => {
        const interval = setInterval(_ => setResquest(!request), timeFetch);
        return () => {
            clearInterval(interval);
        };
    }, [request, setResquest]);

    if(!getGameState){
        return <>
            <NavBar/>
            <p style={{color: "red", fontSize: "50px", textAlign: "center"}}>...Enemy is Playing...</p>
        </>
    }
    const gameState = getGameState.properties.state as string
    const opponentTurn = (gameState === DomainGameState.OPPONENT)
    const myTurn = (gameState === DomainGameState.YOUR_TURN)
    const victory = (gameState === DomainGameState.VICTORY)
    const defeat = (gameState === DomainGameState.DEFEAT)

    console.log("Get Game ->",opponentTurn)
    console.log("Get Game ->",myTurn)
    console.log("Get Game ->",victory)
    console.log("Get Game ->",defeat)

    if(myTurn){
        return <Navigate to={`/game/${gameId}/own-turn`}/>
    }

    if(opponentTurn){
        return <>
            <NavBar/>
            <p style={{color: "red", fontSize: "50px", textAlign: "center"}}>...Enemy is Playing...</p>
        </>
    }
    if(victory){
        delay(9000)
        alert("YOU WON!! CONGRATS")
        return <div>
            <p>YOU WIN!!!!!!!!!</p>
            <Navigate to={"/"}/>
        </div>
    }
    if(defeat){
        delay(9000)
        return <div>
            <p>YOU LOSE!!!!!!!!!</p>
            <Navigate to={"/"}/>
        </div>
    }
}












