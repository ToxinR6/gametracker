import {
  Card,
  CardContent,
  CardMedia,
  Typography,
  Select,
  MenuItem,
  Stack,
  Chip,
  Button,
  Box,
  Tooltip
} from "@mui/material";

import placeholder from "../assets/placeholder-game.jpg";

const STATUSES = [
  "BACKLOG",
  "PLAYING",
  "ON_HOLD",
  "COMPLETED",
  "DROPPED"
];

export default function GameCard({
  game,
  onStatusChange,
  onDelete,
  isSteam,
  animationDelay
}) {
  const tags = Array.isArray(game.tags) ? game.tags : [];
  const description =
    typeof game.notes === "string" && game.notes.trim()
      ? game.notes.trim()
      : "No description";

  return (
    <Card
      className="gt-card"
      sx={{
        display: "flex",
        mb: 2,
        borderRadius: 2,
        height: 120,
        alignItems: "stretch",
        animationDelay
      }}
    >
      <Box sx={{ position: "relative" }}>
        <Tooltip
          arrow
          placement="right"
          title={
            <Box sx={{ p: 1, maxWidth: 320 }}>
              <Typography variant="subtitle2" sx={{ mb: 0.5 }}>
                {game.title}
              </Typography>
              <Typography variant="body2" sx={{ mb: 0.5 }}>
                {description}
              </Typography>
              <Typography variant="caption" sx={{ opacity: 0.8 }}>
                Tags: {tags.length ? tags.join(", ") : "No tags"}
              </Typography>
            </Box>
          }
        >
          <CardMedia
            component="img"
            className="gt-poster"
            sx={{
              width: 240,
              height: 120,
              objectFit: "contain",
              backgroundColor: "grey.100"
            }}
            image={game.imageUrl || placeholder}
            alt={game.title}
            onError={(e) => {
              e.target.onError = null;
              e.target.src = placeholder;
            }}
          />
        </Tooltip>
        {isSteam && (
          <Chip
            label="Steam"
            size="small"
            color="primary"
            className="gt-badge"
            sx={{
              position: "absolute",
              top: 6,
              left: 6,
              zIndex: 1,
              bgcolor: "rgba(13, 71, 161, 0.9)",
              color: "common.white"
            }}
          />
        )}
      </Box>

      <CardContent
        sx={{
          flex: 1,
          py: 0.5,
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-between",
          height: "100%"
        }}
      >
        <Stack
          direction="row"
          spacing={1}
          alignItems="center"
          justifyContent="space-between"
        >
          <Typography variant="subtitle1" noWrap>
            {game.title}
          </Typography>
        </Stack>

        <Typography variant="body2">
          Hours Played: {game.hoursPlayed}
        </Typography>

        <Stack direction="row" spacing={1} alignItems="center" className="gt-actions">
          <Select
            size="small"
            value={game.status}
            onChange={(e) =>
              onStatusChange(game.id, e.target.value)
            }
            sx={{ width: 140 }}
          >
            {STATUSES.map((status) => (
              <MenuItem key={status} value={status}>
                {status.replace("_", " ")}
              </MenuItem>
            ))}
          </Select>

          {!isSteam && (
            <Button
              variant="outlined"
              color="error"
              size="small"
              onClick={() => onDelete(game)}
              sx={{ width: 96 }}
            >
              Delete
            </Button>
          )}
        </Stack>
      </CardContent>
    </Card>
  );
}
