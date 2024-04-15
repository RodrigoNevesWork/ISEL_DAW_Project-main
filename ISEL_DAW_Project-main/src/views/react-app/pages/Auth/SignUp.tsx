import React, {useRef, useState} from 'react';
import NavBar, {Layout} from "../Layout";
import {idPlayerCookie, LoggedInContext, tokenCookie, useSetUser} from "./Authn";
import {Navigate, useLocation, useNavigate} from "react-router-dom";
import {authenticate, loginFetch} from "../../services/PlayerServices";
import {doCookie} from "../../utils/cookieHandler";
import SignErrorHandling from "../../utils/ErrorHandling";


function SignupPage(){
    return (
        <Layout>
            <Signup/>
        </Layout>
    )
}


function Signup() {
    console.log("SignUp")
    const [inputs, setInputs] = useState({
        username: "",
        password: "",
    })
    const currentUser = React.useContext(LoggedInContext)
    const [isSubmitting, setIsSubmitting] = useState(false)
    const [error, setError] = useState(undefined)
    const [redirect, setRedirect] = useState(false)
    const navigate = useNavigate()
    const location = useLocation()

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const name = ev.currentTarget.name
        setInputs({ ...inputs, [name]: ev.currentTarget.value })
        setError(undefined)
    }
    function handleSubmit(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault()
        setIsSubmitting(true)
    }

    function Auth(props:{user:string,pass:string}){
        const res = loginFetch("/api/players", {username:props.user,password:props.pass}).response

        if(!res){
            return <p>...loading...</p>
        }

        if(res.status){
            console.log(res)
            setIsSubmitting(false)
            return <SignErrorHandling message={res.title}/>
        }


        if(res){
            console.log(res)
            const token = res.properties.token as string
            const playerID = res.properties.id
            currentUser.user = {id:playerID,username:props.user}
            doCookie({name:tokenCookie,value : token, expire : undefined, path : undefined})
            doCookie({name:idPlayerCookie,value:playerID,expire : undefined, path : undefined})
        }

        return(
            <div>
                {!res?
                    <p>...loading...</p> :
                    <div>
                        <p>...signUp success...</p>
                        <Navigate to={"/"} replace={true}/>
                    </div>
                }
            </div>
        )
    }
    return (
        <Layout>

            <NavBar/>
            {!isSubmitting?
                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                    <fieldset disabled={isSubmitting}>
                        <h1>Signup</h1>
                        <div>
                            <label htmlFor="username">Username</label>
                            <input id="username" type="text" name="username" value={inputs.username} onChange={handleChange} />
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <input id="password" type="password" name="password" value={inputs.password} onChange={handleChange} />
                        </div>
                        <div>
                            <button type="submit" disabled={!inputs.username || inputs.password.length < 8}>SignUp</button>
                            <a>Already have an account?</a> <a href="/login">LogIn</a>
                        </div>
                    </fieldset>
                    {error}
                </form>:
                <Auth pass={inputs.password} user={inputs.username}/>
            }
        </Layout>
    )
}



export default Signup;
