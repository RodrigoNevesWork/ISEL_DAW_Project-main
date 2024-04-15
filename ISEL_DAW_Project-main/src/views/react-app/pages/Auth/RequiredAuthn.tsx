import {idPlayerCookie, LoggedInContextCookie, tokenCookie} from "./Authn";
import {Link, Navigate, useLocation} from "react-router-dom";
import React from "react";

export function RequireAuthn({ children }: { children: React.ReactNode }): React.ReactElement {
    const tokenUser = React.useContext(LoggedInContextCookie).loggedInState.token
    const location = useLocation()
    console.log(`currentToken = ${tokenUser}`)
    if (tokenUser) {
        return <>{children}</>
    } else {
        console.log("redirecting to login")
        return <Navigate to="/login" state={{source: location.pathname}} replace={true}/>
    }

}