import React, {useContext} from "react";
import {contextGame} from "../Game/ChooseGameSize";
import {Navigate, useParams} from "react-router-dom";


export function RedirectToGame(){

    const gameId = useParams().id
        return <Navigate to={`/game/${gameId}/own-turn`} replace={true}/>
}