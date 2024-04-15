import React, {useState} from 'react';
import {Link, Navigate} from "react-router-dom";


function ErrorPage(){
    window.location.href = "/"
    return(
        <div>
            <p>Something went wrong</p>
            <Link to = {"/"}/>
        </div>
    )
}


export default ErrorPage