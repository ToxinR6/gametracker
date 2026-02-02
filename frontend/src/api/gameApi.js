import api from "./axios";

export const getAllGames = () => api.get("/games");

export const createGame = (game) =>
  api.post("/games", game);

export const updateGameStatus = (id, status) =>
  api.patch(`/games/${id}/status`, null, {
    params: { status }
  });
