import gsAxios from "./index";

const BOARD_URL = '/api/connect4/board';

const fetchBoard = () => gsAxios.get(BOARD_URL);
const newGame = () => gsAxios.get(`${BOARD_URL}/newgame`);
const explodeMine = (row, column) => gsAxios.get(`${BOARD_URL}/explode?row=${row}&column=${column}`);
const dropDisk = (column) => gsAxios.get(`${BOARD_URL}/drop?column=${column}`);
const mineDetect = (column) => gsAxios.get(`${BOARD_URL}/minedetect?column=${column}`);
const randMove = () => gsAxios.get(`${BOARD_URL}/randommove`).catch(err => console.error(err));
const setWinner = (tokenState) => gsAxios.get(`${BOARD_URL}/setwinner?tokenState=${tokenState}`).catch(err => console.error(err));
const undoMove = () => gsAxios.get(`${BOARD_URL}/undoMove`).catch(err => console.error(err));
const init = (row, column, mines) => gsAxios.get(`${BOARD_URL}/init?row=${row}&column=${column}&mines=${mines}`).catch(err => console.error(err));

const boardService = { fetchBoard, newGame, explodeMine, dropDisk, mineDetect, randMove, setWinner, undoMove, init };
export default boardService;
