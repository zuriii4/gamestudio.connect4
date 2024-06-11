import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import "./RatingForm.css";

const RatingForm = (props) => {
    const [selectedRating, setSelectedRating] = useState(5);

    const handleSubmit = (event) => {
        event.preventDefault();
        let player = props.winner === "PLAYER1" ? props.player1 : props.player2;
        props.onSendRating(selectedRating, player);
    };

    const selectRating = (rating) => {
        setSelectedRating(rating);
    };

    return (
        <div className="rating-form-container d-flex justify-content-center align-items-center">
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3 text-center justify-content-center">
                    <Form.Label className="text-white">Rate the Game</Form.Label>
                    <div className="star-rating m-auto">
                        {[1, 2, 3, 4, 5].map((rating) => (
                            <span
                                key={rating}
                                className={`star ${rating <= selectedRating ? "selected" : ""}`}
                                onClick={() => selectRating(rating)}
                            >
                                ★
                            </span>
                        ))}
                    </div>
                    {selectedRating === 0 && (
                        <Form.Text style={{ color: "red" }}>
                            Please select a rating
                        </Form.Text>
                    )}
                </Form.Group>
                <Button
                    variant="primary"
                    type="submit"
                    size="lg"
                    disabled={selectedRating === 0}
                >
                    Submit Rating
                </Button>
            </Form>
        </div>
    );
};

export default RatingForm;
