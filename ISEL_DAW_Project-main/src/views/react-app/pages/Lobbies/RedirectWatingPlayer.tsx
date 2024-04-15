import React from "react";
import {Navigate, useParams} from 'react-router-dom';

export function RedirectWaitingPlayer(props:{state:string,gameId:string}){
    console.log("redirectwaitingplayer state=", props.state)
    return(
        <div>
            {(props.state === "NOT_BEGUN") ?
                <div>
                    <p>...not begun...</p>
                    <Navigate to={`/game/redirect-player/${props.gameId}`} replace={true}/>
                </div>:
                <div>
                    <p>..begun..</p>
                    <Navigate to={`/game/redirect-to-game/${props.gameId}`} replace={true}/>
                </div>
            }
        </div>
    )
}