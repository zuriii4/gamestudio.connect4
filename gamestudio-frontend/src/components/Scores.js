import {Table} from "react-bootstrap";
import './Scores.css';
import React from "react";

function Scores(props) {
    return (
        <div>
            <Table striped bordered hover className=" score_table">
                <thead>
                <tr>
                    <th>Player</th>
                    <th>Score</th>
                </tr>
                </thead>
                <tbody>
                {props.score.map(score => (
                    <tr key={`score-${score.ident}`}>
                        <td>{score.playerName}</td>
                        <td>{score.score}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    )
}

export default Scores;