import * as React from 'react';
import {
    useState,
    createContext,
    useContext, useEffect,
} from 'react';
import  {fetchGetSession} from '../../utils/cookieHandler';

export const tokenCookie = 'cookie'
export const usernameCookie = 'cookie-name'
export const idPlayerCookie = 'cookie-id'
const url = 'background_image.jpg'

export type User = {
    id: any,
    username: string
}

type AuthContextType = {
    user: User | undefined,
    setUser: (newUserInfo: User | undefined) => void
};

export const LoggedInContext = createContext<AuthContextType>({
    user: undefined,
    setUser: () => {}
});

export type SessionState = {
    state: boolean,
    auth: boolean
}

export const LoggedInContextCookie = createContext({
    loggedInState: { state: false, auth: false, token: undefined, id:undefined },
});

export function AuthnContainer({ children }: { children: React.ReactNode }) {
   /* const id = getCookie(idPlayerCookie);
    const user = getCookie(tokenCookie);

    const [data, setData] = React.useState(undefined);

    React.useEffect(() => {
        const token = checkCookie("")
            setData(token)
    }, [data]);

    const info = id !== undefined && user !== undefined ? { id: parseInt(id), username : user } : undefined;

    const [userInfo, setUserInfo] = useState(info);*/

    const [authenticated, setAuthenticated] = useState({ state: false, auth: false, token: undefined, id: undefined})
    useEffect(() => {
        fetchGetSession(
            (token: string, id: string) => {
                console.log("fetchgetsession", token, id)
                if (token) setAuthenticated({ state: true, auth: true , token: token, id: id})
                else setAuthenticated({ state: false, auth: false, token: undefined, id: id })
            }
        )
        return () => { }
    }, [setAuthenticated])

    return (
        <LoggedInContextCookie.Provider value={{loggedInState: authenticated}}>
            { children }
        </LoggedInContextCookie.Provider>
    );
}


export function useCurrentUser() {
    return useContext(LoggedInContext).user;
}

export function useSetUser() {
    return useContext(LoggedInContext).setUser;
}
