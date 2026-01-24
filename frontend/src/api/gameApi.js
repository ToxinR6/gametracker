import api   from "./axios";

export const getAllGames = () => api.get("/games");
export const createGame = (game) => api.post("/games", game);