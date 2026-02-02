import {
  Card,
  CardContent,
  CardMedia,
  Typography,
  Select,
  MenuItem,
  Stack
} from "@mui/material";

import placeholder from "../assets/placeholder-game.jpg";

const STATUSES = [
  "BACKLOG",
  "PLAYING",
  "ON_HOLD",
  "COMPLETED",
  "DROPPED"
];

export default function GameCard({ game, onStatusChange }) {
  return (
    <Card
      sx={{
        display: "flex",
        mb: 2,
        borderRadius: 2
      }}
    >
      <CardMedia
        component="img"
        sx={{ width: 160 }}
        image={game.imageUrl || placeholder}
        alt={game.title}
        onError={(e) =>{
            e.target.onError = null;
            e.target.src = placeholder;
        }}
      />

      <CardContent sx={{ flex: 1 }}>
        <Stack spacing={1}>
          <Typography variant="h6">
            {game.title}
          </Typography>

          <Typography variant="body2">
            Hours Played: {game.hoursPlayed}
          </Typography>

          <Select
            size="small"
            value={game.status}
            onChange={(e) =>
              onStatusChange(game.id, e.target.value)
            }
            sx={{ width: 160 }}
          >
            {STATUSES.map((status) => (
              <MenuItem key={status} value={status}>
                {status.replace("_", " ")}
              </MenuItem>
            ))}
          </Select>
        </Stack>
      </CardContent>
    </Card>
  );
}
