import { useEffect, useState } from "react";
import {
  Container,
  Typography,
  Box,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import { getAllGames } from "../api/gameApi";
import AddGameForm from "../components/AddGameForm";

function GamesPage() {
  const [games, setGames] = useState([]);

  useEffect(() => {
    getAllGames()
      .then((res) => {
        setGames(res.data);
      })
      .catch((err) => {
        console.error("Error fetching games:", err);
      });
  }, []);

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          My Games
        </Typography>
        <AddGameForm setGames={setGames} />
        <List>
          {games.map((game) => (
            <ListItem key={game.id}>
              <ListItemText
                primary={game.title}
                secondary={`${game.status} (${game.hoursPlayed} hrs)`}
              />
            </ListItem>
          ))}
        </List>
      </Box>
    </Container>
  );
}

export default GamesPage;
