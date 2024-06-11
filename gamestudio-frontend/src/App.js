import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { addComment, fetchComments } from "./_api/comment.service";
import React, { useEffect, useState } from "react";
import Comments from "./components/Comments";
import ZurianConnect4 from "./components/game/zurianConnect4/ZurianConnect4";
import { fetchScore } from "./_api/score.service";
import Scores from "./components/Scores";
import {Accordion} from "react-bootstrap";
import LoginForm from "./components/LoginForm";
import boardService from "./_api/board.service";
import {addRating, fetchAverageRating, fetchRating} from "./_api/rating.service";
import {handleNewGame} from "./components/game/zurianConnect4/ZurianConnect4";
import Ratings from "./components/Ratings";

function App() {
    const selectedGame = 'connect4';
    const [loggedPlayer1, setLoggedPlayer1] = useState("");
    const [loggedPlayer2, setLoggedPlayer2] = useState("");
    const [color1, setColor1] = useState("");
    const [color2, setColor2] = useState("");
    const [comments, setComments] = useState([]);
    const [score, setScore] = useState([]);
    const [ratings, setRatings] = useState([]);
    const [averageRating, setAverageRating] = useState(0);
    const [showLoginForm, setShowLoginForm] = useState(true);

    const fetchDataScore = () => {
        fetchScore(selectedGame).then(response => {
            setScore(response.data);
            // console.log(response.data);
        }).catch(error => {
            console.error('Error fetching score:', error);
        });
    }

    const fetchDataComment = () => {
        fetchComments(selectedGame)
            .then(response => {
                const sortedComments = response.data.sort((a, b) => {
                    return new Date(b.commented) - new Date(a.commented);
                });
                setComments(sortedComments);
            })
            .catch(error => {
                console.error('Error fetching comments:', error);
            });
    }

    const fetchDataRating = () => {
        fetchRating(selectedGame)
            .then(response => {
                const sortedRatings = response.data.sort((a, b) => {
                    return new Date(b.rated) - new Date(a.rated);
                });

                setRatings(sortedRatings);
                // console.log(response.data);
            })
            .catch(error => {
                console.error('Error fetching ratings:', error);
            });

        fetchAverageRating(selectedGame)
            .then(response => {
                const avg = parseFloat(response.data);
                setAverageRating(isNaN(avg) ? 0 : avg);
            })
            .catch(error => {
                console.error('Error fetching average rating:', error);
                setAverageRating(0);
            });
    }

    useEffect(() => {
        fetchDataComment();
        fetchDataScore();
        fetchDataRating();
    }, []);

    const handleSendComment = (comment, player) => {
        addComment(selectedGame, player, comment)
            .then(() => {
                fetchDataComment();
            })
            .catch(error => {
                console.error('Error adding comment:', error);
            });
    }
    const handleSendRating = (rating, player) => {
        addRating(selectedGame, player, rating)
            .then(() => {
                fetchDataRating();
            })
            .catch(error => {
                console.error('Error adding rating:', error);
            });
    }

    const handleSubmitLogin = (player1, player2, color1, color2, row, column, mines) => {
        setLoggedPlayer1(player1);
        setLoggedPlayer2(player2);
        setColor1(color1);
        setColor2(color2);
        setShowLoginForm(false);
        console.log(row, column, mines);
        boardService.init(row,column,mines).then(setShowLoginForm(false));
        // console.log("Logged in Player 1:", player1, "Color:", color1);
        // console.log("Logged in Player 2:", player2, "Color:", color2);
    }

    return (
        <div className="App container-sm mt-4 mb-4">
            <div className="parallax-container">
                <div className="parallax-layer layer1" data-depth="0.2"></div>
                <div className="parallax-layer layer2" data-depth="0.4"></div>
                <div className="parallax-layer layer3" data-depth="0.6"></div>
                <div className="parallax-layer layer4" data-depth="0.8"></div>
                <div className="parallax-layer layer5" data-depth="1.0"></div>
                <div className="parallax-layer layer6" data-depth="1.2"></div>
                <div className="parallax-layer layer7" data-depth="1.4"></div>
                <div className="parallax-layer layer8" data-depth="1.6"></div>
            </div>
            <div className="content">
                {/*<span className="text-white">Selected game: {selectedGame}</span>*/}
                {showLoginForm &&
                    <>
                    <h1 className={'gameStudio_header'}>Gamestudio</h1>
                    <LoginForm onSubmitLogin={handleSubmitLogin}/>
                    </>
                }
                {!showLoginForm && (
                    <>
                        <ZurianConnect4
                            player1={loggedPlayer1}
                            player2={loggedPlayer2}
                            color1={color1}
                            color2={color2}
                            fetchScore={fetchDataScore}
                            selectedGame={selectedGame}
                            onSendComment={handleSendComment}
                            onSendRating={handleSendRating}
                        />
                        <Accordion className="custom-accordion">
                            <Accordion.Item eventKey="0" className="custom-accordion-item">
                                <Accordion.Header className="custom-accordion-header score-header">
                                    <h1>Scores</h1>
                                </Accordion.Header>
                                <Accordion.Body className="custom-accordion-body">
                                    <Scores score={score} />
                                </Accordion.Body>
                            </Accordion.Item>

                            <Accordion.Item eventKey="1" className="custom-accordion-item">
                                <Accordion.Header className="custom-accordion-header comments-header">
                                    <h1>Comments</h1>
                                </Accordion.Header>
                                <Accordion.Body className="custom-accordion-body">
                                    <Comments comments={comments} />
                                    {/*<h2>Add comment</h2>*/}
                                    {/*<CommentForm onSendComment={handleSendComment}/>*/}
                                </Accordion.Body>
                            </Accordion.Item>

                            <Accordion.Item eventKey="2" className="custom-accordion-item">
                                <Accordion.Header className="custom-accordion-header rating-header">
                                    <h1>Rating</h1>
                                </Accordion.Header>
                                <Accordion.Body className="custom-accordion-body">
                                    <Ratings
                                        ratings={ratings}
                                        averageRating={averageRating}
                                    />
                                </Accordion.Body>
                            </Accordion.Item>
                        </Accordion>

                    </>
                )}
            </div>
        </div>
    );

}

export default App;
