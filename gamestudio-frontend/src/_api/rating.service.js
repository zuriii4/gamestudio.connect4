import gsAxios from "./index";
import {formatDate} from "./utiil";



export const fetchRating = game => {
    return gsAxios.get("/api/rating/" + game);
}

export const fetchAverageRating = game => {
    return gsAxios.get("/api/rating/average/" + game);
}


export const addRating= (game, player, rating) => gsAxios.post('/api/rating', {
    playerName: player,
    game: game,
    rating: rating,
    rated: formatDate(new Date()),
});