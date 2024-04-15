import {Fetch} from "./useFetch";

export type cookieParams = {
    name : string ,
    value: string ,
    expire: string | undefined,
    path: string | undefined,
};

export function doCookie(cookieParams: cookieParams){
    document.cookie = `${cookieParams.name}=${cookieParams.value}; expire=${cookieParams.expire ? cookieParams.expire : Date.now() + 24 * 60 * 60 * 1000}; path=${cookieParams.path ? cookieParams.path : '/'}`;
}

export function deleteCookie(cookieName: string) {
    document.cookie = `${cookieName}=;expire=${Date.now()}`;
}


export async function fetchGetSession(onSuccess: (token: string, id: string) => void) {
    console.log("fetch get session")
    try {
        const resp = await fetch('/api/check_session', {  credentials: 'include'})
        const response = await resp.json()
        console.log("antes do token")
        const token = response.find(item => item.name === "token")

        console.log("filter token", token)
        const id = response.find(it => it.name === "id")
        console.log("filter id", token)
        onSuccess(token.value, id.value)
    }catch (error){

    }
}

/*
export function getCookie(cookieName: string){
    const resp = Fetch("/check_session", "GET")
    if(resp.response){
        console.log(resp.response.value)
        return resp.response.value
    }
    /*const cookieArray = document.cookie.split(";");
    const requestedPair = cookieArray.find(pair => pair.split("=")[0].trim() === cookieName);
    return requestedPair ? requestedPair.substring(requestedPair.indexOf('=') + 1) : undefined;*/
//}*/
/*
export function checkCookie(cookieName : string){
    console.log(getCookie(cookieName) !== "")
    return getCookie(cookieName) !== ""
}

*/