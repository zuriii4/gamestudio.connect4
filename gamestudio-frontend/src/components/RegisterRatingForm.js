import {Button, Form} from "react-bootstrap";
import React, {useState} from "react";
import {useForm} from "react-hook-form";
import './RatingForm.css';
import './Comments.css';

const RegisterRatingForm = (props) => {
    const [selectedRating, setSelectedRating] = useState(5);

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm({ mode: "onChange" });

    const onSubmit = (data) => {
        const player = props.winner === "PLAYER1" ? props.player1 : props.player2;

        props.onSendRating(selectedRating, player);
        props.onSendComment(data.comment, player);

        reset();
        setSelectedRating(5);
    };

    const selectRating = (rating) => {
        setSelectedRating(rating);
    };

    return (
        <div>
            <Form onSubmit={handleSubmit(onSubmit)} className="comment-form">
                {/* Comment Input */}
                <Form.Group className="mb-3">
                    <Form.Label className="text-white">Enter your comment</Form.Label>
                    <input
                        className={`form-control ${errors.comment ? "is-invalid" : ""}`}
                        type="text"
                        {...register("comment", {
                            minLength: { value: 3, message: "Minimum is 3 characters" },
                            maxLength: { value: 150, message: "Maximum is 150 characters" },
                            required: { value: true, message: "Please enter a valid comment" },
                        })}
                        placeholder="Your comment"
                    />
                    <Form.Text className="text-danger float-right">
                        {errors.comment?.message}
                    </Form.Text>
                </Form.Group>

                {/* Rating Selection */}
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

                {/* Submit Button */}
                <Button
                    variant="primary"
                    type="submit"
                    size="lg"
                    disabled={selectedRating === 0}
                >
                    Submit Rating and Comment
                </Button>
            </Form>
        </div>
    );
}
export default RegisterRatingForm;