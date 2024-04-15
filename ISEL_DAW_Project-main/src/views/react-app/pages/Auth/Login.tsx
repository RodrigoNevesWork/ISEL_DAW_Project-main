import React, {useState} from 'react';
import {LoggedInContext, useSetUser} from "./Authn";
import {Navigate, useLocation} from "react-router-dom";
import {loginFetch} from "../../services/PlayerServices";
import NavBar, {Layout} from "../Layout";
import SignErrorHandling, {LoginErrorHandling} from "../../utils/ErrorHandling";



function Login() {
    const currentUser = React.useContext(LoggedInContext)
    const [inputs, setInputs] = useState({
        username: "",
        password: "",
    })
    const [isSubmitting, setIsSubmitting] = useState(false)
    const [error, setError] = useState(undefined)
    const [redirect, setRedirect] = useState(false)
    const location = useLocation()

    if(redirect) {
        return <Navigate to={location.state?.source?.pathname || "/"} replace={true}/>
    }

    function handleChange(ev: React.FormEvent<HTMLInputElement>) {
        const name = ev.currentTarget.name
        setInputs({ ...inputs, [name]: ev.currentTarget.value })
        setError(undefined)
    }

    function handleSubmit(ev: React.FormEvent<HTMLFormElement>){
        ev.preventDefault()
        setIsSubmitting(true)
    }

    function Auth(props:{user:string,pass:string}){
        const resp = loginFetch("/api/login", {username:props.user,password:props.pass}).response
         if(!resp){
            return <p>...loading...</p>
         }

         if(resp.status){
             setIsSubmitting(false)
             return <LoginErrorHandling message={resp.title}/>
         }

         if(resp){
             const playerID = resp.properties.id
             currentUser.user ={id: playerID, username:props.user }
             window.location.href = "/"
             return <div>
                <p>...login success...</p>
                <Navigate to={location.state?.source?.pathname || "/"} replace={true}/>
             </div>
         }
    }

    return (
        <Layout>
            <NavBar/>
            {!isSubmitting?
                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                    <fieldset disabled={isSubmitting}>
                        <h1 style ={{textAlign: 'center'}}>Login</h1>
                        <div>
                            <label htmlFor="username">Username</label>
                            <input id="username" type="text" name="username" value={inputs.username} onChange={handleChange} />
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <input id="password" type="password" name="password" value={inputs.password} onChange={handleChange} />
                        </div>
                        <div>
                            <p></p>
                            <div style={{ display: 'flex', justifyContent: 'center', width: '100%' }}>
                            <button  type="submit" disabled={!inputs.username || !inputs.password}>Login</button>
                                </div>
                            <p></p>
                            <a>Don't have an account?</a> <a href="/players">Signup</a>
                        </div>
                    </fieldset>
                    {error}
                </form>:
                <Auth pass={inputs.password} user={inputs.username}/>
            }
        </Layout>
    )
}


export default Login;