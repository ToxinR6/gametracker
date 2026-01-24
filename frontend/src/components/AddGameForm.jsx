import { useState } from "react";
import { createGame } from "../api/gameApi";

function AddGameForm({ onGameAdded }) {
  const [title, setTitle] = useState("");

  const submit = async (e) => {

    console.log("Submit Clicked");
    e.preventDefault();

    const newGame = {
      title,
      status: "PLAYING",
      hoursPlayed: 0,
      notes: ""
    };

    const res = await createGame(newGame);
    onGameAdded(res.data);
    setTitle("");
  };

  return (
    <form onSubmit={submit}>
      <input
        value={title}
        onChange={e => setTitle(e.target.value)}
        placeholder="Game title"
        required
      />
      <button>Add</button>
    </form>
  );
}

export default AddGameForm;
