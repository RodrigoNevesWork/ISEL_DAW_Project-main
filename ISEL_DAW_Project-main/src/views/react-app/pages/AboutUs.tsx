import React, { useState } from 'react'
import NavBar, {Layout} from "./Layout";
import {aboutUs} from "../services/PlayerServices";
import {AuthnContainer, LoggedInContext} from "./Auth/Authn";
import {MDBRow} from "mdb-react-ui-kit";


function AboutUsPage() {
    const fetchAboutUsRes = aboutUs("/api/about_us").response
    console.log(fetchAboutUsRes)
    return (
        <Layout>
            <NavBar/>
            <div>
                {!fetchAboutUsRes ?
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
                        <div>About US</div>

                        <div>We are students of software engineering at ISEL and have developed this battleship game
                            for DAW with the intent of learning about typescript.
                        </div>

                        <AuthorContact contact={"https://www.linkedin.com/in/rodrigo-neves-758539182/"}
                                       name={fetchAboutUsRes.properties.authors[0].name}
                                       email={fetchAboutUsRes.properties.authors[0].email}/>
                        <AuthorContact contact={"https://www.linkedin.com/in/mafalda-rodrigues-1486a6b6/"}
                                       name={fetchAboutUsRes.properties.authors[1].name}
                                       email={fetchAboutUsRes.properties.authors[1].email}/>
                        <AuthorContact contact={"https://www.linkedin.com/in/in%C3%AAs-martins-45a9b722b/"}
                                       name={fetchAboutUsRes.properties.authors[2].name}
                                       email={fetchAboutUsRes.properties.authors[2].email}/>
                        <SystemVersion system={fetchAboutUsRes.properties.systemVersion}/>
                    </div>
                }
            </div>
        </Layout>
    )
}
function AuthorContact(props: { contact: string, name: string,email:string }) {
    return (
        <div style={{ fontFamily:"sans-serif",display:'flex',alignItems:'center',justifyContent:'center',flexDirection:'column',gap:'20px',backgroundColor:'#f8f9fd' }}>
            <MDBRow>
                <a
                    href={props.contact}
                    className="disableLink"
                    style={{ overflow: "hidden", textOverflow: "ellipsis", textDecoration: "none" }}
                >
                    {props.name} {props.email}
                </a>
            </MDBRow>
        </div>
    )
}

function SystemVersion(props: { system: string}) {
    return (
        <div>
        <MDBRow>
        <p>System Version:</p>
        <p style={{ fontFamily:"sans-serif",display:'flex',alignItems:'center',justifyContent:'center',flexDirection:'column',gap:'20px',backgroundColor:'#f8f9fd' }}>
            {props.system}
        </p>
        </MDBRow>
        </div>
    )
}
export default AboutUsPage