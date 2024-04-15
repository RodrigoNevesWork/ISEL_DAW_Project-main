import React, {useContext, useEffect, useState} from 'react';
import {setNewGame} from "../../services/gameServices";
import {LoggedInContextCookie} from "../Auth/Authn";
import {createContext} from 'react';
import {Navigate, useLocation} from "react-router-dom";
import NavBar, {Layout} from "../Layout";
import {RedirectLobby} from "../Lobbies/RedirectLobby";
import {delay} from "../../utils/useFetch";

type ContextType = {
    boardSize: number
    deadline: number
    idPlayer: number
    gameId:string
}

export const contextGame = createContext<ContextType>({
    boardSize: 0,
    deadline: 0,
    idPlayer: 0,
    gameId: ""
})

export function GameContext({ children }: { children: React.ReactNode}){
    const [board, setBoardSize] = useState(0)
    const [deadLine, setDeadline] = useState(0)
    const [player, setIdPlayer] = useState(0)
    const [game, setIdGame] = useState("")

    useEffect(() => {
        setBoardSize(board)
        setDeadline(deadLine)
        setIdPlayer(player)
        setIdGame(game)
    },[board,deadLine,player,game])

    return (
        <contextGame.Provider value={{ boardSize: board, deadline: deadLine, idPlayer:player,gameId:game }}>
            {children}
        </contextGame.Provider>
    )
}


function StartGameTemplate(){
    const ctx = useContext(contextGame);
    console.log("Login")
    const currentUser = React.useContext(LoggedInContextCookie)
    const getId = currentUser.loggedInState.token
    const [inputs, setInputs] = useState({
        boardsize: 0,
        deadline: 0
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
        console.log(name)
        console.log(ev.currentTarget.value)
        setInputs({ ...inputs, [name]: ev.currentTarget.value })
        setError(undefined)
    }

    function handleSubmit(ev: React.FormEvent<HTMLFormElement>){
        ev.preventDefault()
        setIsSubmitting(true)
    }

    function FetchLobby(props:{board_size:number,deadline:number}){
        const board_size = props.board_size
        const deadline = props.deadline
        console.log("fetch lobby = ", board_size, deadline)
        const response = setNewGame({board_size: board_size, deadline: deadline}).response

        if(response){
            delay(2000)
            ctx.gameId = response.properties.id
            ctx.boardSize = props.board_size
            ctx.deadline = props.deadline
            ctx.idPlayer = getId
        }


        return(
            <div>
                {!response ?
                    <p>...loading...</p> :
                    <div>
                        <RedirectLobby/>
                    </div>
                }
            </div>
        )
    }
    const [value1, setValue1] = useState(6);
    const [value2, setValue2] = useState(200);

    const handleChange1 = (event) => {
        setValue1(event.target.value);
    };

    const handleChange2 = (event) => {
        setValue2(event.target.value);
    };

    return (
        <Layout>
           <NavBar/>
            {!isSubmitting?
                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                    <fieldset disabled={isSubmitting}>
                        <h1>Choose your game settings</h1>
                        <div className="slidecontainer">
                            <input style={{ appearance: 'none', width:'100%', height: 15, background: '#ddd', outline: 'none', opacity: 0.7, transition: 'opacity 0.2s',}}
                                type="range" min="6" max="10" value={value1} className="slider" id="myRange" onChange={handleChange1}/>
                            <p>Boardsize: <span id="demo">{value1}</span></p>
                        </div>
                        <div className="slidecontainer">
                            <input style={{ appearance: 'none', width:'100%', height: 15, background: '#ddd', outline: 'none', opacity: 0.7, transition: 'opacity 0.2s',}}
                                type="range" min="200" max="1000" value={value2} className="slider" id="myRange" onChange={handleChange2}/>
                            <p>Deadline: <span id="demo">{value2}</span></p>
                        </div>
                        <div style={{ display: 'flex', justifyContent: 'center', width: '100%' }}>
                            <button style={{ backgroundColor: '#3e73b6', borderRadius: '10px', border: '4px double #cccccc', color: '#ffffff', textAlign: 'center', fontSize: '28px', padding: '20px', width: '200px', transition: 'all 0.5s', cursor: 'pointer', margin: '5px' }}
                                    type="submit">Create</button>
                        </div>
                    </fieldset>
                    {error}
                </form>:
                <FetchLobby deadline={value2} board_size={value1}/>
            }
        </Layout>
    )
}

export default StartGameTemplate;
