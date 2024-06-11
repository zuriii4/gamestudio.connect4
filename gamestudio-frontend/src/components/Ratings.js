// components/Ratings.js
import { Table } from "react-bootstrap";
// import './Ratings.css';
import React from "react";

const Ratings = ({ ratings, averageRating }) => {



    const formattedAverageRating = typeof averageRating === 'number' ? averageRating.toFixed(2) : "N/A";

    return (
        <div>
            <Table striped bordered hover className="ratings_table">
                <thead>
                <tr>
                    <th>Player</th>
                    <th>Rating</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {ratings.map(rating => (
                    <tr key={`rating-${rating.ident}`}>
                        <td>{rating.playerName}</td>
                        <td>{rating.rating}</td>
                        <td>{new Date(rating.rated).toLocaleDateString()}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <div className="average-rating">
                <h4>Average Rating: {formattedAverageRating}</h4>
            </div>
        </div>
    );
}

export default Ratings;
