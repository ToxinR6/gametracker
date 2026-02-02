import { useEffect, useState } from "react";
import {
  Container,
  Typography,
  Box
} from "@mui/material";
import { getAllGames, updateGameStatus } from "../api/gameApi";
import AddGameForm from "../components/AddGameForm";
import GameCard from "../components/GameCard";

function GamesPage() {
  const [games, setGames] = useState([]);

  useEffect(() => {
    getAllGames()
      .then((res) => setGames(res.data))
      .catch((err) =>
        console.error("Error fetching games:", err)
      );
  }, []);

  const handleStatusChange = async (id, status) => {
    await updateGameStatus(id, status);
    setGames((prev) =>
      prev.map((g) =>
        g.id === id ? { ...g, status } : g
      )
    );
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" gutterBottom>
          My Games
        </Typography>

        <AddGameForm setGames={setGames} />

        {games.map((game) => (
          <GameCard
            key={game.id}
            game={game}
            onStatusChange={handleStatusChange}
          />
        ))}
      </Box>
    </Container>
  );
}

export default GamesPage;
