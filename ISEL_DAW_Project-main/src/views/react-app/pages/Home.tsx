import React, {useState} from 'react';
import {LoggedInContext, LoggedInContextCookie} from "./Auth/Authn";
import NavBar, {Layout} from "./Layout";
import {Navigate} from "react-router-dom";
import {MDBRow} from "mdb-react-ui-kit";


function Home(){
    const currentUser = React.useContext(LoggedInContextCookie).loggedInState.state
    console.log("Cookie ID",currentUser)
    return(
        <div style = {{backgroundImage: "url(/back.png)"}}>
            <Layout>
                <div>
                <NavBar/>
                    <h1 style={{color: "#3e73b6", textAlign: 'center'}}>Welcome to the BattleShip</h1>
                    {currentUser?
                        <MDBRow style={{display: "flex", justifyContent: "center"}}>
                            <ButtonGame log={true}/>
                        </MDBRow>
                        :
                        <MDBRow style={{display: "flex", justifyContent: "center"}}>
                            <ButtonGame log={false}/>
                        </MDBRow>
                    }
                </div>
            </Layout>
        </div>
    );
}


function ButtonGame(props:{ log:boolean}) {
    const [submit,SetSubmit] = useState(false)
    function onClickEventLog() {
        SetSubmit(true)
       return
    }
    function onClickEventNotlog() {
        window.location.href = '/login'
    }
    return(
        <div>
            {submit? <Navigate to={"/games"} replace={true}/>:<></>}
            {props.log ?
                <button style={{
                    display: 'inline-block',
                    padding: '15px 25px',
                    fontSize: '24px',
                    cursor: 'pointer',
                    textAlign: 'center',
                    textDecoration: 'none',
                    outline: 'none',
                    color: "#fff",
                    backgroundColor: "#4CAF50",
                    border: 'none',
                    borderRadius: '15px',
                    boxShadow: '0 9px #999'
                }} onClick={onClickEventLog} type={"submit"}>Start Game</button>
                :
                <button style={{
                    display: 'inline-block',
                    padding: '15px 25px',
                    fontSize: '24px',
                    cursor: 'pointer',
                    textAlign: 'center',
                    textDecoration: 'none',
                    outline: 'none',
                    color: "#fff",
                    backgroundColor: "#4CAF50",
                    border: 'none',
                    borderRadius: '15px',
                    boxShadow: '0 9px #999'
                }} onClick={onClickEventNotlog} type={"submit"}>Start Game</button>
            }
        </div>
)}


export default Home;