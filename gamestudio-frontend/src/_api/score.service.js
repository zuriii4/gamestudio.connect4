import gsAxios from "./index";
import {formatDate} from "./utiil";



export const fetchScore = game => {
    return gsAxios.get("/api/score/" + game);
}


export const addScore = (game, player, score) => gsAxios.post('/api/score', {
    playerName: player,
    game: game,
    score: score,
    played: formatDate(new Date()),
});