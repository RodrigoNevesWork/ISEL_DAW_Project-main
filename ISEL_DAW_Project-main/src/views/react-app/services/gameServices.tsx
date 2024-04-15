import React from "react";
import {Fetch, FetchResponse, FetchMultipleRequests} from "../utils/useFetch";

export type Player = {
    id : number,
    username : string,
    password : string,
    points : number|null,
    token : string
}


export function fetchCheckLobby(props:{player: number}){
    const body = JSON.stringify(props)
    console.log("body ->",body)
    return FetchMultipleRequests("/api/games/lobby?player=" + props.player,'get', undefined)
}


export function setNewGame(props:{board_size: number,deadline: number}): FetchResponse{
    console.log("setNewGame",props)
    const body = JSON.stringify(props)
    console.log("body "+ body)
    const a =  Fetch("/api/games", "POST", body)
    console.log("set new game fetch", a)
    return a
}

export function getStateGame(gameId:string){
    return FetchMultipleRequests('/api/games/'+gameId, 'get', undefined)
}

export function getGameSimple(gameId:string){
    console.log("antes  do get ")
    const a = Fetch('/api/games/'+gameId,'GET',undefined)
    console.log("depois do get")
    return a
}

export function placingBoats(inputShip: { ship: string; position: string; direction: string}[], idGame: string): FetchResponse{
    const body = JSON.stringify(inputShip)
    console.log(body)
    return Fetch(`/api/games/${idGame}/placing_phase`, "POST", body)
}

export function doShot(props:{gameId:string,position:string}){
    if(props.position){

        const body = `{
        "position":"${props.position}"
         }
    `
        return Fetch("/api/games/"+props.gameId,'POST',body)
    }else {
        return undefined
    }
}