import React from 'react'
import {Link, useLocation} from "react-router-dom";
import {AuthnContainer, LoggedInContextCookie, tokenCookie} from "./Auth/Authn";
import {logOut} from "../services/PlayerServices";



export function Layout(props){
    return(
        <AuthnContainer>
            {props.children}
        </AuthnContainer>
    )
}

function NavBar(){
    const check =  React.useContext(LoggedInContextCookie).loggedInState.state
    console.log("Check",check)
    const location = useLocation()
    return(
        <AuthnContainer>
            <div>
                {!check?
                    <>
                        <ul style={{listStyleType: 'none', margin: 0, padding: 0, overflow: 'hidden', backgroundColor: '#3e73b6'}}>
                            <li style={{float: 'left'}}>
                                <Link to={"/"} state={{source: location.pathname}} style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}} replace={true}>
                                    Home </Link>
                            </li>
                            <li style={{float: 'left'}}>
                                <Link to = "/about_us" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}>About us</Link>
                            </li>

                            <li style={{float: 'right'}}>
                                <Link to = "/login" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}>Login</Link>
                            </li>
                            <li style={{float: 'right'}}>
                                <Link to = "/players" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}>Signup</Link>
                            </li>
                            <li style={{float: 'left'}}>
                                <Link to = "/ranking" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}>Ranking</Link>
                            </li>
                        </ul>
                    </>
                    :
                    <>
                        <ul style={{listStyleType: 'none', margin: 0, padding: 0, overflow: 'hidden', backgroundColor: '#3e73b6'}}>
                            <li style={{float: 'left'}}>
                                <Link to = "/" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}> Home </Link>
                            </li>
                            <li style={{float: 'left'}}>
                                <Link to = "/about_us" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}>About us</Link>
                            </li>

                            <li style={{float: 'right'}}>
                                <li style={{float: 'right'}}>
                                    <Link to = "/" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}} onClick={() => logOut(() => {
                                        window.location.href = '/'
                                    })}>Logout</Link>
                                </li>
                            </li>
                            <li style={{float: 'left'}}>
                                <Link to = "/ranking" className="active" style={{display: 'block', color: 'white', textAlign: 'center', padding: '14px 16px', textDecoration: 'none'}}>Ranking</Link>
                            </li>
                        </ul>
                    </>
                }
            </div>
        </AuthnContainer>
    );
}


export default NavBar;