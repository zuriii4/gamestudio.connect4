import { useEffect, useState } from "react";
import Button from 'react-bootstrap/Button';
import boardService from "../../../_api/board.service";
import Board from "./Board";
import './Connect4.css';
import WinnerPopup from "./WinnerPopup";
import {addScore} from "../../../_api/score.service";


function ZurianConnect4(props) {
    const [board, setBoard] = useState(null);
    const [mineDetectionStatus, setMineDetectionStatus] = useState({});
    const [mineLock, setMineLock] = useState(false);
    const [solved, setSolved] = useState(false);
    const [winner, setWinner] = useState("");
    const [currentPlayer, setCurrentPlayer] = useState('PLAYER1');
    const [showPopup, setShowPopup] = useState(false);
    const [timerActive, setTimerActive] = useState(true);


    const handleClosePopup = () => setShowPopup(false);
    const handleShowPopup = () => setShowPopup(true);

    useEffect(() => {
        boardService.fetchBoard().then((response) => {
            const boardData = response.data;
            setBoard(boardData);
            setSolved(boardData.gameState === "SOLVED");
            if (boardData.gameState === "SOLVED") {
                setWinner(boardData.winner);
                addScore(props.selectedGame, boardData.winner === 'PLAYER1' ? props.player1 : props.player2, boardData.score).then(() => {
                    props.fetchScore();
                })
                handleShowPopup();
            }
        });
    },[]);
    //     const timer = setTimeout(() => {
    //         if (!solved) {
    //             const nextWinner = currentPlayer === "PLAYER1" ? "PLAYER2" : "PLAYER1";
    //             boardService.setWinner(nextWinner).then(() => {
    // //                 console.log(`timeout: ${nextWinner}`);
    //                 setCurrentPlayer(nextWinner);
    //             });
    //         }
    //     }, 5000);
    //
    //     return () => clearTimeout(timer);
    // }, [currentPlayer, solved]);

    const handleDropDisk = (column) => {
        if (solved || mineLock) return;
        boardService.dropDisk(column).then((response) => {
            const boardData = response.data;
            // console.log(boardData);
            setBoard(boardData);
            setSolved(boardData.gameState === "SOLVED");

            if (boardData.gameState === "SOLVED") {
                setWinner(boardData.winner);
                addScore(props.selectedGame,boardData.winner === 'PLAYER1'? props.player1 : props.player2, boardData.score).then(() => {
                    props.fetchScore();
                })
                handleShowPopup();
            }
            // if(!handleMineDetect(column)) {
            //     setCurrentPlayer(currentPlayer === 'Player1' ? 'PLAYER2' : 'PLAYER1');
            setCurrentPlayer(boardData.currentPlayer);
            // }
        });
    };

    const handleExplodeMine = (row, column) => {
        if (solved) return;
        boardService.explodeMine(row, column).then((response) => {
            const boardData = response.data;
            setBoard(boardData);
            setSolved(boardData.gameState === "SOLVED");
            if (boardData.gameState === "SOLVED") {
                setWinner(boardData.winner);
                addScore(props.selectedGame,boardData.winner === 'PLAYER1'? props.player1 : props.player2, boardData.score).then(() => {
                    props.fetchScore();
                })
                handleShowPopup();
            }
            setMineLock(false);
        });
    };

    const handleNewGame = () => {
        boardService.newGame().then((response) => {
            const boardData = response.data;
            setBoard(boardData);
            setMineDetectionStatus({});
            setMineLock(false);
            setSolved(boardData.gameState === "SOLVED");
            setWinner("");
            setCurrentPlayer(boardData.currentPlayer);
        });
    };

    const handleUndoMove = () => {
        boardService.undoMove().then((response) => {
            const boardData = response.data;
            setBoard(boardData);
            setMineDetectionStatus({});
            setMineLock(false);
            setSolved(boardData.gameState === "SOLVED");
            setWinner("");
            setCurrentPlayer(boardData.currentPlayer);
        })
    }

    const handleRandomMove = () => {
        boardService.randMove().then((response) => {
            const boardData = response.data;
            setBoard(boardData);
            setMineDetectionStatus({});
            setMineLock(false);
            setSolved(boardData.gameState === "SOLVED");
            setWinner("");
            setCurrentPlayer(boardData.currentPlayer);
        })
    }

    const handleMineDetect = (column) => {
        if (solved) return;
        return boardService.mineDetect(column).then((response) => {
            setMineDetectionStatus((prevStatus) => ({
                ...prevStatus,
                [column]: response.data,
            }));
            if (response.data === true) {
                setMineLock(true);
            }
        });
    };

    const isMineDetected = (column) => {
        return mineDetectionStatus[column] === true;
    };

    return (
        <div>
            <h1 className="connect4_title">Connect 4</h1>
            {board &&
                board.tokens && (
                    <>
                        {solved ? (
                            <span className="text-light">
                                Congrat {board.winner === 'PLAYER1' ? props.player1 : props.player2} you won
                            </span>
                        ) : (!mineLock ? (
                            <span className="text-light">
                                On the move is: {currentPlayer === 'PLAYER1' ? props.player1 : props.player2}
                            </span>
                        ) : (
                            <span
                                className="text-light">{currentPlayer === 'PLAYER1' ? props.player2 : props.player1} can detonate a mine</span>
                        ))}

                        <Board
                            tokenBoard={board.tokens}
                            dropDisk={handleDropDisk}
                            explodeMine={handleExplodeMine}
                            mineDetect={handleMineDetect}
                            isMineDetected={isMineDetected}
                            mineLock={mineLock}
                            player1={props.player1}
                            player2={props.player2}
                            color1={props.color1}
                            color2={props.color2}
                        />
                    </>
                )
            }
            <div className="ZurianConnect4__wrapper">
                <Button className="new-game-button m-3" variant="secondary" size="lg" onClick={handleNewGame}>
                    New Game
                </Button>

                <Button className="new-game-button m-3" variant="secondary" size="lg" onClick={handleRandomMove}>
                    Random
                </Button>
                <Button className="new-game-button m-3" variant="secondary" size="lg" onClick={handleUndoMove}>
                    Undo
                </Button>
            </div>

            <WinnerPopup show={showPopup}
                         winner={winner}
                         player1={props.player1}
                         player2={props.player2}
                         onClose={handleClosePopup}
                         onSendComment={props.onSendComment}
                         onSendRating={props.onSendRating}
            />
        </div>
    );
}

export default ZurianConnect4;

