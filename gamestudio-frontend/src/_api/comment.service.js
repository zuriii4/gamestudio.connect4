import gsAxios from "./index";
import {formatDate} from "./utiil";

//get api/comment/connect4
//post api/comment/

export const fetchComments = game => {                     // = function(game) {} || game => - arrow function
    return gsAxios.get("/api/comment/" + game);
}
// const fetchComments = game => return gsAxios.get("/api/comment/" + game);

export const addComment = (game, player, comment) => gsAxios.post('/api/comment', {
    playerName: player,
    game: game,
    commentText: comment,
    commented: formatDate(new Date()),
});
