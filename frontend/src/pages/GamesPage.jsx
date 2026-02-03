import { useEffect, useState } from "react";
import {
  Container,
  Typography,
  Box,
  Tabs,
  Tab,
  TextField,
  Button,
  Chip,
  LinearProgress,
  Stack
} from "@mui/material";
import {
  getAllGames,
  updateGameStatus,
  syncSteamGames,
  deleteGame
} from "../api/gameApi";
import AddGameForm from "../components/AddGameForm";
import GameCard from "../components/GameCard";
import bannerImage from "../assets/banner.svg";

const STATUS_FILTERS = [
  { label: "All", value: "ALL" },
  { label: "Playing", value: "PLAYING" },
  { label: "Backlog", value: "BACKLOG" },
  { label: "Completed", value: "COMPLETED" },
  { label: "Dropped", value: "DROPPED" }
];

function GamesPage() {
  const [games, setGames] = useState([]);
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [steamInput, setSteamInput] = useState("");
  const [isSyncing, setIsSyncing] = useState(false);
  const [syncError, setSyncError] = useState("");
  const [parallaxOffset, setParallaxOffset] = useState(0);
  const [lastSyncAt, setLastSyncAt] = useState(() => {
    return localStorage.getItem("gt:lastSyncAt") || "";
  });
  const [lastSyncStatus, setLastSyncStatus] = useState(() => {
    return localStorage.getItem("gt:lastSyncStatus") || "idle";
  });
  const [lastSyncInput, setLastSyncInput] = useState(() => {
    return localStorage.getItem("gt:lastSyncInput") || "";
  });

  useEffect(() => {
    getAllGames()
      .then((res) => {
        setGames(res.data);
      })
      .catch((err) =>
        console.error("Error fetching games:", err)
      );
  }, []);

  useEffect(() => {
    let ticking = false;
    const handleScroll = () => {
      if (ticking) return;
      ticking = true;
      window.requestAnimationFrame(() => {
        setParallaxOffset(window.scrollY * 0.25);
        ticking = false;
      });
    };
    handleScroll();
    window.addEventListener("scroll", handleScroll, { passive: true });
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const handleStatusChange = async (id, status) => {
    await updateGameStatus(id, status);
    setGames((prev) =>
      prev.map((g) =>
        g.id === id ? { ...g, status } : g
      )
    );
  };

  const handleDelete = async (game) => {
    if (game.steamAppId) return;
    try {
      await deleteGame(game.id);
    } catch (err) {
      console.error("Error deleting game:", err);
    } finally {
      setGames((prev) => prev.filter((g) => g.id !== game.id));
    }
  };

  const runSteamSync = async (input) => {
    if (!input.trim()) return;
    setIsSyncing(true);
    setSyncError("");
    setLastSyncStatus("syncing");
    localStorage.setItem("gt:lastSyncStatus", "syncing");
    try {
      await syncSteamGames(input.trim());
      setLastSyncInput(input.trim());
      localStorage.setItem("gt:lastSyncInput", input.trim());
      const res = await getAllGames();
      setGames(res.data);
      const now = new Date().toISOString();
      setLastSyncAt(now);
      setLastSyncStatus("success");
      localStorage.setItem("gt:lastSyncAt", now);
      localStorage.setItem("gt:lastSyncStatus", "success");
    } catch (err) {
      console.error("Error syncing Steam:", err);
      setSyncError("Steam sync failed. Check the input and try again.");
      setLastSyncStatus("failed");
      localStorage.setItem("gt:lastSyncStatus", "failed");
    } finally {
      setIsSyncing(false);
    }
  };

  const handleSteamSync = async () => {
    await runSteamSync(steamInput);
  };

  const handleResync = async () => {
    const input = steamInput.trim() || lastSyncInput.trim();
    await runSteamSync(input);
  };

  const visibleGames =
    statusFilter === "ALL"
      ? games
      : games.filter((g) => g.status === statusFilter);

  const formatSyncTime = (iso) => {
    if (!iso) return "Never synced";
    const date = new Date(iso);
    if (Number.isNaN(date.getTime())) return "Unknown";
    return date.toLocaleString();
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Box
          sx={{
            height: 180,
            borderRadius: 3,
            mb: 3,
            backgroundImage: `url(${bannerImage})`,
            backgroundSize: "cover",
            backgroundPosition: `center ${-parallaxOffset}px`,
            boxShadow: "0 16px 40px rgba(0,0,0,0.35)"
          }}
        />

        <Typography variant="h4" gutterBottom>
          My Games
        </Typography>

        <Box sx={{ display: "flex", gap: 2, flexWrap: "wrap", mb: 1 }}>
          <TextField
            label="Steam Profile URL or Steam ID"
            value={steamInput}
            onChange={(e) => setSteamInput(e.target.value)}
            sx={{ minWidth: 280, flex: 1 }}
          />
          <Button
            variant="contained"
            onClick={handleSteamSync}
            disabled={isSyncing || !steamInput.trim()}
          >
            {isSyncing ? "Syncing..." : "Steam Sync"}
          </Button>
          <Button
            variant="outlined"
            onClick={handleResync}
            disabled={isSyncing || !(steamInput.trim() || lastSyncInput.trim())}
          >
            Resync
          </Button>
        </Box>
        <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
          <Typography variant="body2" sx={{ opacity: 0.8 }}>
            Last Sync: {formatSyncTime(lastSyncAt)}
          </Typography>
          <Chip
            size="small"
            label={
              lastSyncStatus === "syncing"
                ? "Syncing"
                : lastSyncStatus === "success"
                ? "Success"
                : lastSyncStatus === "failed"
                ? "Failed"
                : "Idle"
            }
            color={
              lastSyncStatus === "success"
                ? "success"
                : lastSyncStatus === "failed"
                ? "error"
                : lastSyncStatus === "syncing"
                ? "info"
                : "default"
            }
            variant={lastSyncStatus === "idle" ? "outlined" : "filled"}
          />
        </Stack>
        {isSyncing && (
          <LinearProgress
            color="info"
            sx={{ mb: 2, borderRadius: 1 }}
          />
        )}
        {syncError && (
          <Typography variant="body2" color="error" sx={{ mb: 2 }}>
            {syncError}
          </Typography>
        )}

        <AddGameForm setGames={setGames} />

        <Tabs
          value={statusFilter}
          onChange={(_, value) => setStatusFilter(value)}
          sx={{ mt: 2, mb: 2 }}
        >
          {STATUS_FILTERS.map((filter) => (
            <Tab
              key={filter.value}
              label={filter.label}
              value={filter.value}
            />
          ))}
        </Tabs>

        {visibleGames.length === 0 && (
          <Box className="gt-empty">
            <Typography variant="h6">No games yet</Typography>
            <Typography variant="body2">
              Add a game or sync your Steam library to get started.
            </Typography>
          </Box>
        )}

        {visibleGames.map((game, index) => (
          <GameCard
            key={game.id}
            game={game}
            onStatusChange={handleStatusChange}
            onDelete={handleDelete}
            isSteam={
              Boolean(game.steamAppId) ||
              Boolean(
                game.imageUrl &&
                  game.imageUrl.includes("steamstatic.com/steam/apps/")
              )
            }
            animationDelay={`${index * 40}ms`}
          />
        ))}
      </Box>
    </Container>
  );
}

export default GamesPage;
