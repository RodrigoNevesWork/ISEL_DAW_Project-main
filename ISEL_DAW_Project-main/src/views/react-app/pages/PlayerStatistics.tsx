
import React from 'react';
import {getRanking} from "../services/PlayerServices";
import {LoggedInContext} from "./Auth/Authn";
import NavBar from "./Layout";
import {DomainPlayerOutput} from "../utils/DomainTypes";




function PlayerStats(){
    const currentUser = React.useContext(LoggedInContext).user? React.useContext(LoggedInContext).user.username:undefined
    const fetchRankingsRes = getRanking().response
    console.log(fetchRankingsRes)
    return (
            <div> <NavBar/>
                {!fetchRankingsRes ?
                    <p>..loading Page ..</p> :
                    <div style=
                             {{
                                 fontFamily: "sans-serif",
                                 display: 'flex',
                                 alignItems: 'center',
                                 justifyContent: 'center',
                                 flexDirection: 'column',
                                 gap: '20px',
                                 backgroundColor: '#f8f9fd'
                             }}
                         className="form">
                        
                      <RankingOutput ars={fetchRankingsRes.properties.ranking}/>
                    </div>
                }
            </div>
    )

}



function RankingOutput(props:{ars:Array<DomainPlayerOutput>}){
    return (
        <div style ={{fontFamily: "Arial"}}>
        <p style={{ alignContent:"center",color:'#3e73b6'}}>Players Rankings </p>
        <table style={{
            fontFamily: "Arial, Helvetica, sans-serif",
            borderCollapse: "collapse",
            width: "100%",
            alignContent:"center"
        }}>
            <tr>
                <td style={{ border:"1px solid #ddd",padding:"8px",backgroundColor:"#3e73b6",color: 'white'}}>username</td>
                <td style={{ border:"1px solid #ddd",padding:"8px",backgroundColor:"#3e73b6",color: 'white'}}>points</td>
                <td style={{ border:"1px solid #ddd",padding:"8px",backgroundColor:"#3e73b6",color: 'white'}}>number of games</td>
            </tr>
            {props.ars.map((player: DomainPlayerOutput) => (
                <tr key={player.id} style={{
                    border: "1px solid #ddd",
                    padding: "8px"
                }}>
                    <td style={{
                        paddingTop: "12px",
                        paddingBottom: "12px",
                        textAlign: "left",color: '#3e73b6'
                    }}>{player.username}</td>
                    <td style={{
                        paddingTop: "12px",
                        paddingBottom: "12px",
                        textAlign: "left",color: '#3e73b6'
                    }}>{player.points}</td>
                    <td style={{
                        paddingTop: "12px",
                        paddingBottom: "12px",
                        textAlign: "left",color: '#3e73b6'
                    }}>{player.nrOfGames}</td>
                </tr>
            ))}
        </table>
        </div>
    );
}



export default PlayerStats;