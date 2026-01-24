import { useEffect, useState } from "react";
import { getAllGames } from "../api/gameApi";
import AddGameForm from "../components/AddGameForm";

function GamesPage() {
  const [games, setGames] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllGames()
      .then(res => setGames(res.data))
      .catch(err => console.error(err))
      .finally(() => setLoading(false));
  }, []);

  const addGameToList = (game) => {
    setGames(prev => [...prev, game]);
  };

  if (loading) return <p>Loading games...</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>My Games</h1>

      <AddGameForm onGameAdded={addGameToList} />

      {games.length === 0 ? (
        <p>No games yet</p>
      ) : (
        <ul>
          {games.map(game => (
            <li key={game.id}>
              <strong>{game.title}</strong> — {game.status} — {game.hoursPlayed} hrs
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default GamesPage;
