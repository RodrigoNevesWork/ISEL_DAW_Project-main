import {useParams} from "react-router-dom";
import {getGameSimple} from "../../services/gameServices";
import {State, TargetState} from "../../utils/DomainTypes";
import {DrawBoard} from "./DrawBoard";
import * as React from "react";
import {wrapperStyle} from "./PlacingBoats";
import NavBar from "../Layout";


function MyTurn(){
    const gameId = useParams().id
    const getGameState = getGameSimple(gameId).response

    if(!getGameState){
        return <><NavBar/> <p style={{color: "blue", fontSize: "50px", textAlign: "center"}} >... Now is your turn ...</p></>
    }

    const myBoard = getGameState.properties.ownBoard
    const eneBoard = getGameState.properties.enemyBoard
    const sizeBoard = getGameState.properties.size

    console.log("begin initial state")
    const state = initialState()
    console.log("End initial state")

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
                    } else if (coord.state === 'HitShip'){
                        rowArray.push({ coords: { column: col + 1, row: row + 1 }, hit: 'HIT' });
                    } else {
                        rowArray.push({ coords: { column: col + 1, row: row + 1 }, hit: 'DOWN' });
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
                    } else if (coord.state === 'HitShip'){
                        rowArr.push({ coords: { column: col + 1, row: row + 1 }, hit: 'HIT' });
                    } else {
                        rowArr.push({ coords: { column: col + 1, row: row + 1 }, hit: 'DOWN' });
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
            <NavBar/>
            <div style={{display: "flex", alignItems: "center"}}>
                <div style={{...wrapperStyle, display: "inline-block", width: "50%"}}>
                    <p style={{display: "flex", alignItems: "center",color:'#2fa5c9',fontSize:'25px'}} >My Game</p>
                    <DrawBoard gameId={gameId} state={state.ownBoard}/>
                </div>
                <div style={{...wrapperStyle,display:"inline-block", width: "50%"}}>
                    <p style={{display: "flex", alignItems: "center",color:'#2fa5c9',fontSize:'25px'}}> Enemy Game </p>
                    <DrawBoard gameId={gameId} state={state.enemyBoard}/>
                </div>
            </div>
        </div>
    )
}

export default MyTurn