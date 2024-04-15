import * as React from "react"
import {useCallback, useReducer, useState} from "react"
import {placingBoats} from "../../services/gameServices";
import {FetchResponse} from "../../utils/useFetch";
import {RedirectWaitingPlayer} from "../Lobbies/RedirectWatingPlayer";
import {BoatType, Coordinates, gameType, SourceState} from "../../utils/DomainTypes";
import NavBar from "../Layout";
import {useParams} from "react-router-dom";


type State = {
    sources: Array<BoatType>
    targets: Array<Array<TargetState>>
}

type Action =
    | {type: 'drop', sourceIx: number, coordinate:Coordinates, boardSize: number}
    | {type: 'rotate', idxBoat : number}

type TargetState =
    | Coordinates
    | undefined


export const wrapperStyle: React.CSSProperties = {
    display : 'grid',
    gridTemplateColumns: "repeat(1, 2fr)",
    gridAutoRows: "minmax(100px, auto)"
}

export const wrapperStyleBoard: React.CSSProperties = {
    display: 'grid',
    gridGap: '0',
    gridTemplateColumns: "repeat(3, 1fr)",
    gridAutoRows: "minmax(100px, auto)"
}



function sourceDivStyle(boatSize:number, sourceState: SourceState, column: number, row: number, rotated: string): React.CSSProperties {
    console.log(`${rotated}`)
    return {
        border: 'solid',
        width: boatSize * 55 + 'px',
        height: '55px',
        backgroundColor: '#2fa5c9',
        borderColor: sourceState === 'available' ? 'white' : 'red',
        transform: rotated === 'V' ? 'rotate(90deg)' : 'rotate(0deg)'
    };
}


function targetDivStyle(targetState:TargetState, column:number, row:number): React.CSSProperties {
    return {
        gridColumn: column,
        gridRow: row,
        border: "solid",
        width: "50px",
        height: "50px",
        borderColor: targetState === undefined ? '#2fa5c9' : 'red',
        backgroundColor: '#2fa5c9'
    }
}


function rotate (coordinates:Coordinates, boat:BoatType): React.CSSProperties {
    console.log(`coor = ${coordinates.row} ${coordinates.column} boat = ${boat.ship}`)
    return {
        gridColumn: coordinates.column,
        gridRow: coordinates.row,
        border: "solid",
        width: "50px",
        height: "50px",
        borderColor: boat.sourceState == 'available' ? 'green' : 'red',
        position: 'absolute',
        left: `${coordinates.row * 50}px`,
        top: `${coordinates.column * 50}px`,
        transform: `rotate(${boat.direction == "H" ? 0 : 90}deg)`
    }
}


function reduce(state: State, action: Action): State {
    if(action.type=='drop'){
        const boat = state.sources[action.sourceIx]
        const coords = Array<Coordinates>(boat.piece)
        for(let i = 0; i < boat.piece; i++){
            if (boat.direction == 'H'){
                if(action.coordinate.column + i + 1 > action.boardSize) return state
                if((state.targets[action.coordinate.row][action.coordinate.column + i + 1]) !== undefined) return state
                coords.push({column: action.coordinate.column + i, row:action.coordinate.row})
            } else {
                if(action.coordinate.row + i + 1 > action.boardSize) return state
                if((action.coordinate.row + i + 1 !== action.boardSize) &&  (state.targets[action.coordinate.row + i + 1][action.coordinate.column]) !== undefined) return state
                coords.push({column: action.coordinate.column, row:action.coordinate.row + i})
            }
        }
        return {
            sources:state.sources.map((value, ix) =>                                      // 0 -> A, 1 -> B (......)
                ix == action.sourceIx ? { ...value, sourceState: 'used', position : `${String.fromCharCode(action.coordinate.column+65)}${action.coordinate.row+1}` } : value), // cada posicao do array continua igual excepto o campo sourceState
            targets: state.targets.map((row, rowIx) => row.map((value, colIx) => {
                if (coords.some(coord => coord.column == colIx && coord.row == rowIx)) {
                    return action.coordinate;
                } else {
                    return value;
                }
            }))
        }
    }
    if (action.type == 'rotate'){
        return {
            sources:state.sources.map((value, ix) =>
                // every property of source remains the same except direction
                ix == action.idxBoat ? { ...value, direction: value.direction == 'H' ? 'V' : 'H'} : value),
            targets: state.targets
        }
    }
    return state
}

function handleDragStart(event: React.DragEvent<HTMLDivElement>) {
    event.currentTarget.classList.add('dragging');
    const ix = event.currentTarget.attributes.getNamedItem('data-ix').textContent
    event.dataTransfer.effectAllowed = "all"
    event.dataTransfer.setData("text/plain", ix)
}

function handleDragOver(event: React.DragEvent<HTMLDivElement>) {
    event.currentTarget.classList.remove('dragging');
    event.preventDefault()

    const ix = event.currentTarget.attributes.getNamedItem('data-ix').textContent

    event.dataTransfer.setData("text/plain", ix)
    event.dataTransfer.dropEffect = "copy"
}

function isAllBoatsUsed(state: State): boolean {
    const sources = state.sources
    for(let i = 0; i < sources.length; ++i)
        if(sources[i].sourceState === 'available') return false
    return true
}


function submitBoard(state:State, gameId: string): FetchResponse {
    const filteredArray = state.sources.map(({ ship, direction, position }) => ({ ship, direction, position}));
    return placingBoats(filteredArray, gameId)
}


function PlaceBoard(props:{gameId: string, state: State}) {
    console.log("gameid", props.gameId)
    const resp = submitBoard(props.state, props.gameId).response
    console.log("resp = ", resp)
    return(
        <div>
            {!resp?
                <p>..loading..</p>:
                <div>
                    <RedirectWaitingPlayer state={resp.properties.state} gameId={props.gameId}/>
                </div>
            }
        </div>
    )
}

const PlacingBoatsPage: React.FC = () => {
    const [submitStartGame, setSubmitStartGame] = useState(false)
    const gameId = useParams().id//React.useContext(contextGame).gameId//getGameSize.size

    const sizeBoard = parseInt(useParams().board)
    const arrayBoats = gameType(sizeBoard)

    const initialState = {
        sources:arrayBoats,
        targets: new Array(sizeBoard).fill([]).map(() => new Array(sizeBoard).fill(undefined)),
    }

    const [state, dispatch] = useReducer(reduce, initialState)


    const handleDrop = useCallback(function handleDrop(event: React.DragEvent<HTMLDivElement>) {
        const sourceIx = parseInt(event.dataTransfer.getData("text/plain"))
        const targetIx = parseInt(event.currentTarget.attributes.getNamedItem('data-ix').textContent)
        const targetIy = parseInt(event.currentTarget.attributes.getNamedItem('data-iy').textContent)

        dispatch({
            'type': 'drop',
            sourceIx: sourceIx,
            coordinate: {row: targetIx, column: targetIy},
            boardSize: sizeBoard
        })

    }, [dispatch])

    function handleRotate(current: number) {
        dispatch({
            type: "rotate",
            idxBoat: current,
        });
    }



    function handleSubmit(ev: React.FormEvent<HTMLFormElement>){
        ev.preventDefault()
        setSubmitStartGame(true)
    }

    return(
            <div>
                <NavBar/>
                {!submitStartGame?
                    <div>
                        <p style={{fontSize:"25px",color:"#2fa5c9"}}>Place your boats on the board</p>
                        <div style={wrapperStyle}>
                            <table>
                                <tbody>
                                {state.sources.map((source, i) => (
                                    <td key={`${i}`}>
                                        <p></p>
                                        <div>
                                            <div
                                                style={sourceDivStyle(source.piece, source.sourceState, i+1, 1, source.direction)}
                                                draggable={source.sourceState == 'available'}
                                                onDragStart={handleDragStart}
                                                // boat index of state.sources that is being dragged
                                                data-ix={i}
                                            >
                                            </div>
                                        </div>
                                        <p></p>
                                        <p></p>
                                        {source.piece !== 1 && (
                                            <button onClick={() => handleRotate(i)} disabled = {source.sourceState == 'used'}> rotate </button>
                                        )}
                                    </td>
                                ))}
                                </tbody>
                            </table>
                        </div>
                        <table style={wrapperStyleBoard}>
                            <tbody>
                            {state.targets.map((row, rowIx) => (
                                <tr key={rowIx}>
                                    {row.map((target, colIx) => (
                                        <td key={`${rowIx}-${colIx}`}>
                                            <div
                                                style={targetDivStyle(target, rowIx + 1, colIx + 1)}
                                                onDragOver={target === undefined ? handleDragOver : undefined}
                                                onDrop={target === undefined ? handleDrop : undefined}
                                                data-ix={rowIx}
                                                data-iy={colIx}
                                            ></div>
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                        <form onSubmit={handleSubmit}>
                            <button type="submit" disabled={!isAllBoatsUsed(state)}>Start new game</button>
                        </form>
                    </div>:
                    <PlaceBoard  gameId={gameId} state={state}/>
                }</div>
    )
}

export default PlacingBoatsPage