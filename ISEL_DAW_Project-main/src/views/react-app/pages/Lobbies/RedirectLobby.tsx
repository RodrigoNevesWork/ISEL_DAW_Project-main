import React, {useContext} from "react";
import {contextGame} from "../Game/ChooseGameSize";
import {Navigate} from "react-router-dom";


export function RedirectLobby(){
    const ctx = useContext(contextGame)

    if(ctx.gameId){///games/placing-boats/:id/:board
        return <Navigate to={"/games/placing-boats/"+ctx.gameId+"/"+ctx.boardSize} replace={true}/>
    }
    if(!ctx.gameId){
        return <Navigate to={"/games/lobby"} replace={true}/>
    }

}