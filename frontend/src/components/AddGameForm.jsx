import { useState } from "react";
import { Box, TextField, Button } from "@mui/material";

function AddGameForm({ setGames }) {
  const [title, setTitle] = useState("");
  const [hoursPlayed, setHoursPlayed] = useState("");

  const handleAddGame = () => {
    const newGame = {
      id: Date.now(), // Use a temporary unique ID
      title: title,
      status: "PLAYING",
      hoursPlayed: Number(hoursPlayed),
    };
    setGames((prevGames) => [...prevGames, newGame]);
    setTitle("");
    setHoursPlayed("");
  };

  return (
    <Box
      component="form"
      sx={{
        "& > :not(style)": { m: 1, width: "25ch" },
      }}
      noValidate
      autoComplete="off"
    >
      <TextField
        id="title"
        label="Game Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <TextField
        id="hoursPlayed"
        label="Hours Played"
        type="number"
        value={hoursPlayed}
        onChange={(e) => setHoursPlayed(e.target.value)}
      />
      <Button variant="contained" onClick={handleAddGame}>
        Add Game
      </Button>
    </Box>
  );
}

export default AddGameForm;
