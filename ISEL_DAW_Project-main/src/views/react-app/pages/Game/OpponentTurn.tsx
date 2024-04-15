import {useParams} from "react-router-dom";
import {getStateGame} from "../../services/gameServices";
import {DrawBoard} from "./DrawBoard";
import * as React from "react";
import {wrapperStyle} from "./PlacingBoats";
import {DomainGameState, State, TargetState} from "../../utils/DomainTypes";


function OpponentTurn(){
    const gameId = useParams().id
    const getGameState = getStateGame(gameId).content
    console.log("getGameState == ", getGameState)
    if(!getGameState){
        return <p>...Loading Game...</p>
    }

    const gameState = getGameState.properties.state as string
    const myBoard = getGameState.properties.ownBoard
    const eneBoard = getGameState.properties.enemyBoard
    const sizeBoard = getGameState.properties.size

    console.log("getGameState depois == ", getGameState)
    const state = initialState()
    console.log("STATE",state.ownBoard)


    function initialState():State {
        const ownBoard: TargetState[][] = [];
        const enemyBoard : TargetState[][] = [];

        for (let row = 0; row < sizeBoard; row++) {
            const rowArray: TargetState[] = [];
            for (let col = 0; col < sizeBoard; col++) {
                const coord = myBoard.find(i => parseInt(i.ship.position.row.ordinal) === row + 1 && parseInt(i.ship.position.column.ordinal) === col + 1);
                if (coord) {
                    if (coord.state === 'OwnShip') {
                        rowArray.push({ coords: { column: col + 1, row: row + 1 }, hit: undefined });
                    } else if (coord.state === 'MissShip') {
                        rowArray.push({ coords: { column: col + 1, row: row + 1 }, hit: 'MISS' });
                    } else {
                        rowArray.push({ coords: { column: col + 1, row: row + 1 }, hit: 'HIT' });
                    }
                } else {
                    rowArray.push({ coords: undefined, hit: undefined });
                }
            }
            ownBoard.push(rowArray);
        }

        for (let row = 0; row < sizeBoard; row++) {
            const rowArr: TargetState[] = [];
            for (let col = 0; col < sizeBoard; col++) {
                const coord = eneBoard.find(i => parseInt(i.ship.position.row.ordinal) === row + 1 && parseInt(i.ship.position.column.ordinal) === col + 1);
                if (coord) {
                    if (coord.state === 'OwnShip') {
                        rowArr.push({ coords: { column: col + 1, row: row + 1 }, hit: undefined });
                    } else if (coord.state === 'MissShip') {
                        rowArr.push({ coords: { column: col + 1, row: row + 1 }, hit: 'MISS' });
                    } else {
                        rowArr.push({ coords: { column: col + 1, row: row + 1 }, hit: 'HIT' });
                    }
                } else {
                    rowArr.push({ coords: undefined, hit: undefined });
                }
            }
            enemyBoard.push(rowArr);
        }

        return {
            ownBoard: ownBoard,
            enemyBoard: enemyBoard //enemyBoardArray
        }

    }
    return(
        <div>
            <div>
                <p>My Game</p>
                <div style={{...wrapperStyle, display: "inline-block"}}>
                    <DrawBoard gameId={gameId} state={state.ownBoard}/>
                    </div></div>:
                <div>
                    <p>My Game</p>
                    <div style={{...wrapperStyle, display: "inline-block"}}>
                        <DrawBoard gameId={gameId} state={state.ownBoard}/>
                    </div>
                    <div style={{display:"inline-block"}}>
                        <p> Enemy Game </p>
                        <DrawBoard gameId={gameId} state={state.enemyBoard}/>
                    </div>
                </div>
            </div>
    )
}
export default OpponentTurn