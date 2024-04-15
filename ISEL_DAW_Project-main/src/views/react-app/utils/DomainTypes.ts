
export type ErrorType = {
    httpStatus:number,
    message:string
}

export type DomainPlayerOutput = {
    id:number,
    username:string,
    points:number,
    nrOfGames:number
}

export type PlayerCredentials = {
    username:string,
    password:string
}

export enum DomainGameState {
    OPPONENT = "OPPONENT_TURN",
    YOUR_TURN = "YOUR_TURN",
    VICTORY = "VICTORY",
    NOT_BEGUN = "NOT_BEGUN",
    DEFEAT = "DEFEAT"
}



export type Coordinates = {
    column:number|undefined,
    row:number|undefined
}


export type TargetState = {
    coords: Coordinates | undefined,
    hit: AccertShot,
    //type:ShipType
}
//export type ShipType = "SUBMARINE"|"DESTROYER"|"DESTROYER"
export type AccertShot = "HIT" | "MISS" | "DOWN" |undefined

export type State = {
    ownBoard: Array<Array<TargetState>> | undefined
    enemyBoard: Array<Array<TargetState>> | undefined
}


export type SourceState =
    | 'available'
    | 'used'


export type BoatType = {
    ship:string
    piece:number,
    direction:string,
    position:string,
    sourceState: SourceState,
    rotation:boolean,
}


export function gameType(size: number): Array<BoatType>{
    if(6<=size && size<=7){
        return [
            {ship: "SUBMARINE", piece: 2, direction: "H", position: "", sourceState: "available"},
            {ship: "SUBMARINE", piece: 2, direction: "H", position: "", sourceState: "available"},
            {ship: "DESTROYER", piece: 1, direction: "H", position: "", sourceState: "available"},
            {ship: "CRUISER", piece: 3, direction: "H", position: "", sourceState: "available"},
            {ship: "BATTLESHIP", piece: 4, direction: "H", position: "", sourceState: "available"}] as Array<BoatType>
    }
    if(8<=size && size<=9){
        return [
            {ship: "SUBMARINE", piece: 2, direction: "H", position: "", sourceState: "available"},
            {ship: "CRUISER", piece: 3, direction: "H", position: "", sourceState: "available"},
            {ship: "CRUISER", piece: 3, direction: "H", position: "", sourceState: "available"},
            {ship: "DESTROYER", piece: 1, direction: "H", position: "", sourceState: "available"},
            {ship: "BATTLESHIP", piece: 4, direction: "H", position: "", sourceState: "available"},
            {ship: "CARRIER", piece: 5, direction: "H", position: "", sourceState: "available"}] as Array<BoatType>

    }
    if(size == 10){
        return [
            {ship: "SUBMARINE", piece: 2, direction: "H", position: "", sourceState: "available"},
            {ship: "CRUISER", piece: 3, direction: "H", position: "", sourceState: "available"},
            {ship: "CRUISER", piece: 3, direction: "H", position: "", sourceState: "available"},
            {ship: "DESTROYER", piece: 1, direction: "H", position: "", sourceState: "available"},
            {ship: "DESTROYER", piece: 1, direction: "H", position: "", sourceState: "available"},
            {ship: "BATTLESHIP", piece: 4, direction: "H", position: "", sourceState: "available"},
            {ship: "BATTLESHIP", piece: 4, direction: "H", position: "", sourceState: "available"},
            {ship: "CARRIER", piece: 5, direction: "H", position: "", sourceState: "available"}] as Array<BoatType>
    }
}
