import React, {useEffect, useState} from "react";
import {LoggedInContextCookie, tokenCookie} from "../pages/Auth/Authn";

import axios from "axios";
const HOST = "http://localhost:8081"

export type FetchResponse = {
    response:any,
    error:any
}
export function delay(delayInMs: number) {
    return new Promise((resolve, reject) => {
        setTimeout(() => resolve(undefined), delayInMs)
    })
}

export function Fetch(url: string, method: string, requestBody: any = null): FetchResponse{
    const [content, setContent] = useState(undefined)
    const [error, setError] = useState(undefined)

    const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    const authorization = token ? {'Content-Type': 'application/json','Authorization': `Bearer ${token} `} : {'Content-Type': 'application/json'}
    const hostUrl = HOST + url

    useEffect(() => {
        let cancelled = false
        async function doFetch() {
            try{
                const resp = await fetch(hostUrl,{
                    method : method,
                    body : requestBody,
                    headers : authorization,
                    //mode: 'no-cors'
                })
                const body = await resp.json()
                if (!cancelled) {
                    setContent(body)
                }
            }catch (error){
                setError(error)
            }
        }
        setContent(undefined)
        doFetch()
        return () => {
            cancelled = true
        }
    }, [url,method,requestBody])
    return {response:content,error:error}
}




function fetch2(url: string, method: string, data:any, headers: any, setContent: (value: any) => void, setError: (value: any) => void) {
    let cancelled = false;
    function doFetch() {
        axios(url, { baseURL: HOST, method: method, data: data, headers }).then(res =>
            res.data
        ).then(body => {
            if (!cancelled) setContent(body);
        }).catch((err) => {
            setError(err);
        });
    }
    setContent(undefined);
    doFetch();
    return () => {
        cancelled = true;
    }
}


export function FetchMultipleRequests(url: string, method: string, data: any): any {
    const [content, setContent] = useState(undefined);
    const [error, setError] = useState(undefined);
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    const authorization = token ? { 'Authorization': 'Bearer ' + token } : undefined;
    useEffect(() => {
        fetch2(url, method, data, authorization, setContent, setError)
    });
    return { content, error };
}