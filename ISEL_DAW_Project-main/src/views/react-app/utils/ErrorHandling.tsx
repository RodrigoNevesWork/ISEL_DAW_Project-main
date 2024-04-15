import React, {useState} from 'react';
import {Navigate} from "react-router-dom";


enum ErrorSignType{
    USER_ALREADY_EXISTS = "User already exists",
    UNSAFE_PASSWORD = "Unsafe password"
}

enum ErrorLoginType {
    LOGIN_FAILED = "Login failed"
}

function SignErrorHandling(props:{message:string}){
    if(ErrorSignType.USER_ALREADY_EXISTS === props.message){
        alert(props.message)
        return <div>
             <Navigate to={"/login"}/>
        </div>
    }

    if(ErrorSignType.UNSAFE_PASSWORD === props.message){
        alert(props.message)
        return <div>
            <p>weak password</p>
            <Navigate to={"/players"}/>
        </div>
    }

}

export function LoginErrorHandling(props:{message:string}){

    if(ErrorLoginType.LOGIN_FAILED === props.message) {
        alert("Username or password incorrect")
        return <div>
            <p>weak password</p>
            <Navigate to={"/login"}/>
        </div>
    }
}
export default SignErrorHandling;
/*
function ErrorHandling(props:{message:string}){

    const [error,setError] = useState(undefined)
    setError(props.message)

    return( <div>
            { error?<div>
                    <p>{error}</p>
                    <Navigate to={"/"}/>
            </div>:
                <p>...Something went wrong...</p>
            }
        </div>
    )
}

export default ErrorHandling;


*/