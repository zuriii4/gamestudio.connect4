import React from "react";
import { Modal, Col, Container, Row } from "react-bootstrap";
import CommentForm from "../../CommentForm";
import winnerImage from "../../../images/winner.png";
import RatingForm from "../../RatingForm";
import RegisterRatingForm from "../../RegisterRatingForm";

const WinnerPopup = (props) => {
    const { show, onClose, winner, player1, player2, onSendComment, onSendRating } = props;

    const winnerName = winner === "PLAYER1" ? player1 : player2;

    return (
        <Modal
            show={show}
            onHide={onClose}
            backdrop="static"
            keyboard={false}
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>Congratulations!</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Container>
                    <Row className="justify-content-center mb-4">
                        <Col xs="auto" className="text-center">
                            <img
                                src={winnerImage}
                                alt="winner"
                                style={{ width: "60%", marginBottom: "1rem" }}
                            />
                            <p>{winnerName} is the winner!</p>
                        </Col>
                    </Row>
                    <Row className="justify-content-center">
                        <Col xs="auto">
                            {/*<CommentForm*/}
                            {/*    onSendComment={onSendComment}*/}
                            {/*    winner={winner}*/}
                            {/*    player1={player1}*/}
                            {/*    player2={player2}*/}
                            {/*/>*/}
                            {/*<RatingForm*/}
                            {/*    onSendRating={onSendRating}*/}
                            {/*    winner={winner}*/}
                            {/*    player1={player1}*/}
                            {/*    player2={player2}*/}
                            {/*/>*/}
                            <RegisterRatingForm
                            winner={winner}
                            player1={player1}
                            player2={player2}
                            onSendRating={onSendRating}
                            onSendComment={onSendComment}
                            />

                        </Col>
                    </Row>
                </Container>
            </Modal.Body>
        </Modal>
    );
};

export default WinnerPopup;
