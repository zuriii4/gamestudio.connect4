import React from "react";
import { Button, Form } from "react-bootstrap";
import { useForm } from "react-hook-form";
import "./CommentForm.css"; // Custom styles

function CommentForm(props) {
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm({ mode: "onChange" });

    const onSubmit = (data) => {
        const player = props.winner === "PLAYER1" ? props.player1 : props.player2;
        props.onSendComment(data.comment, player);

        reset();
    };

    return (
        <div className="comment-form-container d-flex justify-content-center align-items-center">
            <Form onSubmit={handleSubmit(onSubmit)} className="comment-form">
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
                <Button variant="primary" type="submit" size="lg">
                    Send
                </Button>
            </Form>
        </div>
    );
}

export default CommentForm;
