import 'bootstrap/dist/css/bootstrap.min.css';
import Token from "./Token";
import './Board.css';
import React, { useState } from "react";

const Board = (props) => {
    const [highlightedCol, setHighlightedCol] = useState(-1);

    const handleMouseEnterColumn = (colIndex) => {
        setHighlightedCol(colIndex);
    };

    const handleMouseLeaveColumn = () => {
        setHighlightedCol(-1);
    };

    return (
        <div className="board_wrapper container-fluid">
            <table className="board_table">
                <tbody>
                {props.tokenBoard.map((row, rowIndex) => (
                    <tr key={`row-${rowIndex}`}>
                        {row.map((token, colIndex) => (
                            <Token
                                key={`token-${rowIndex}-${colIndex}`}
                                tokenState={token.tokenState}
                                rowIndex={rowIndex}
                                colIndex={colIndex}
                                dropDisk={() => props.dropDisk(colIndex)}
                                explodeMine={(row, col) => props.explodeMine(row, col)}
                                mineDetect={(col) => props.mineDetect(col)}
                                mineDetected={props.isMineDetected(colIndex)}
                                mineLock={props.mineLock}
                                color1={props.color1}
                                color2={props.color2}
                                highlightedCol={highlightedCol}
                                onMouseEnterColumn={() => handleMouseEnterColumn(colIndex)}
                                onMouseLeaveColumn={() => handleMouseLeaveColumn()}
                            />
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Board;
