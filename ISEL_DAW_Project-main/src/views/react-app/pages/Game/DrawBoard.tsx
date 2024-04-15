import * as React from "react";
import {useState} from "react";
import {doShot} from "../../services/gameServices";
import {TargetState} from "../../utils/DomainTypes";
import {Navigate} from "react-router-dom";
import {delay} from "../../utils/useFetch";

function targetDivStyle(targetState:TargetState, column:number, row:number): React.CSSProperties{
    console.log("style = ", targetState.coords, targetState.hit)
    return {
        gridColumn: column,
        gridRow: row,
        border: "solid",
        width: "50px",
        height: "50px",
        borderColor: targetState.coords === undefined ? '#2fa5c9' : targetState.hit === undefined ? '#C0C0C0' : targetState.hit === 'HIT'? 'green' : targetState.hit === 'MISS'? 'red' : 'green',
        backgroundColor:targetState.hit==="DOWN"? 'green':targetState.coords!==undefined&&targetState.hit===undefined?'#C0C0C0':'#2fa5c9',
    }
}

const wrapperStyleBoard: React.CSSProperties = {
    display: 'grid',
    gridTemplateColumns: "repeat(3, 1fr)",
    gridAutoRows: "minmax(100px, auto)"
}

export function  FetchDoShot(props:{gameId:string,position: string}){
    const res = doShot({gameId: props.gameId, position: props.position})
    const url = "/game-play/"+props.gameId
    console.log("Fetch do shot =",res)
    if(!res){
        return <p>Loading</p>
    }
    if(!res.response){
        return <p>SHOT DONE</p>
    }else{
        delay(4000)
        return (
            <>
                <Navigate to={url}/>
                <p>OK</p>
            </>
        );
    }
}


export function DrawBoard(props:{gameId:string,state: Array<Array<TargetState>>}){
    const[coords, setCoords] = useState<string|undefined>(undefined)
    console.log(coords)

    const handleClick = (rowIx: number, colIx: number) => {
        setCoords(`${ String.fromCharCode(colIx+ 64)}${rowIx}`)
    };

    return (
        <div>
            {!coords?
                <table style={wrapperStyleBoard}>
                    <tbody>
                    {props.state.map((row, rowIx) => (
                        <tr key={rowIx}>
                            {row.map((target, colIx) => (
                                <td key={`${rowIx}-${colIx}`}>
                                    <div
                                        style={targetDivStyle(target, rowIx + 1, colIx + 1)}
                                        data-ix={rowIx}
                                        data-iy={colIx}
                                        onClick={() => handleClick(rowIx+1, colIx+1)}
                                    ></div>
                                </td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table>:
                <div>
                    <FetchDoShot gameId={props.gameId} position={coords}/>
                    <table style={wrapperStyleBoard}>
                        <tbody>
                        {props.state.map((row, rowIx) => (
                            <tr key={rowIx}>
                                {row.map((target, colIx) => (
                                    <td key={`${rowIx}-${colIx}`}>
                                        <div
                                            style={targetDivStyle(target, rowIx + 1, colIx + 1)}
                                            data-ix={rowIx}
                                            data-iy={colIx}
                                            onClick={() => handleClick(rowIx+1, colIx+1)}
                                        ></div>
                                    </td>
                                ))}
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            }
        </div>
    )
}


