import {Table} from "react-bootstrap";
import './Comments.css';
import React from "react";

function Comments(props) {
    return (
        <div>
            <Table striped bordered hover className="comments_table">
                <thead>
                <tr>
                    <th>Player</th>
                    <th>Comment</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {props.comments.map(comment => (
                    <tr key={`comment-${comment.ident}`}>
                        <td>{comment.playerName}</td>
                        <td>{comment.commentText}</td>
                        <td>{new Date(comment.commented).toLocaleDateString()}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    )
}

export default Comments;