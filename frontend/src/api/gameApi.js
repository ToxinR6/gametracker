import api from "./axios";

export const getAllGames = () => api.get("/games");

export const createGame = (game) =>
  api.post("/games", game);

export const updateGameStatus = (id, status) =>
  api.patch(`/games/${id}/status`, null, {
    params: { status }
  });

export const syncSteamGames = (steamInput) =>
  api.post("/steam/sync", null, {
    params: { steamInput }
  });

export const deleteGame = (id) => api.delete(`/games/${id}`);
