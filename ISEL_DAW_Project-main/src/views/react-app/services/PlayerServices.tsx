import React, {useEffect} from "react";
import {deleteCookie, doCookie} from "../utils/cookieHandler";
import {idPlayerCookie, LoggedInContextCookie, tokenCookie, usernameCookie, useSetUser} from "../pages/Auth/Authn";
import {Fetch, FetchResponse} from "../utils/useFetch";
import {PlayerCredentials} from "../utils/DomainTypes";
import {redirect} from "react-router-dom";


export interface PlayerServices {
    createPlayer: (username: string, password: string) => void
    getPlayerInfo: (id: number) => void
    getRankings: () => void
    login: () => void
}

export function createPlayer(prop:PlayerCredentials): FetchResponse{
    const body = JSON.stringify(prop)
    console.log(`BODY = ${body}`)
    return Fetch("/players", "POST", body)
}

export function aboutUs(url:string):  FetchResponse{
    return Fetch(url, "GET")
}
export  function getRanking(): FetchResponse {
       return Fetch("/api/ranking", "GET")
}

export function getPlayerById(id : number): FetchResponse {
    const res = Fetch("/players"+id, "GET");
    return res;
}


export function authenticate(url:string,playerCredentials:PlayerCredentials): FetchResponse {
    const authenticationFetch = Fetch(url,"GET",JSON.stringify(playerCredentials))

    if(authenticationFetch.error) return { response: undefined, error : "authentication Error"}

    const setUser = useSetUser()
    const auth = authenticationFetch.response.properties
    const playerID = auth.id

    useEffect(()=>{
        setUser({id : playerID, username : playerCredentials.username})
        },[authenticationFetch])

    doCookie({name:tokenCookie,value : auth.token, expire : undefined, path : undefined})
    doCookie({name:idPlayerCookie,value:playerID,expire : undefined, path : undefined})
    return {response:{userId:playerID ,username : playerCredentials.username},error:undefined}
}

export function loginFetch(url:string,playerCredentials:PlayerCredentials): FetchResponse{
    const body = `{
         "username":"${playerCredentials.username}",
         "password":"${playerCredentials.password}"
    }`
    const body1 = JSON.stringify(playerCredentials)
    console.log("BODY "+body)
    //const body = JSON.stringify(playerCredentials console.log(body)
    return Fetch(url,"POST",body1)
}

export async function logOut(onSuccess: () => void) {
    try {
        const response = await fetch('/api/logout', {  method : 'post',credentials: 'include'})
        if (response.ok) {
            onSuccess()
        }
    } catch (error) {
        //onError()
    }
}

