import React from "react";
import "./Token.css";

const colorToClass = (color) => {
    switch (color) {
        case "#006dc8":
            return "blue";
        case "#cf0000":
            return "red";
        case "#c500cf":
            return "pink";
        case "#cab900":
            return "yellow";
        case "#006400":
            return "dark-green";
        default:
            return "";
    }
};

const Token = (props) => {
    let tokenClass = props.tokenState === "EMPTY" ? "" : props.tokenState.toLowerCase();

    if (props.tokenState === "PLAYER1") {
        tokenClass += ` ${colorToClass(props.color1)}`;
    } else if (props.tokenState === "PLAYER2") {
        tokenClass += ` ${colorToClass(props.color2)}`;
    }

    const handleTokenClick = async () => {
        if (props.mineLock) {
            if (props.mineDetected) {
                props.explodeMine(props.rowIndex, props.colIndex);
            }
        } else {
            await props.mineDetect(props.colIndex);

            if (props.mineDetected) {
                props.explodeMine(props.rowIndex, props.colIndex);
            } else {
                props.dropDisk(props.colIndex);
            }
        }
    };

    const highlightClass = props.colIndex === props.highlightedCol ? "highlight" : "";

    return (
        <td
            className={`token-container ${highlightClass}`}
            onClick={handleTokenClick}
            onMouseEnter={props.onMouseEnterColumn}
            onMouseLeave={props.onMouseLeaveColumn}
        >
            <div className={`token ${tokenClass}`}></div>
        </td>
    );
};

export default Token;
