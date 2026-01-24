🎮 GameTracker

GameTracker is a full-stack application that helps you track your video games, playtime, and completion status.
It also supports importing games directly from Steam using a Steam ID.

Built with:

Backend: Spring Boot (Java)

Frontend: React (Vite)

Database: MySQL

API Integration: Steam Web API

✨ Features

📋 Add, update, delete games manually

⏱ Track hours played

🏷 Game status using enums (PLAYING, COMPLETED, BACKLOG)

🎮 Import owned games from Steam

🔁 Prevent duplicate Steam imports

🌐 REST API + React frontend

🏗 Project Structure
gametracker/
│
├── backend/                # Spring Boot application
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
│
├── frontend/               # React (Vite) app
│   ├── src
│   ├── package.json
│   └── vite.config.js
│
└── README.md

⚙️ Backend Setup (Spring Boot)
Prerequisites

Java 21+

Maven

MySQL

Database

Create a database:

CREATE DATABASE game_tracker;

application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/game_tracker
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

steam.api.url=https://api.steampowered.com
steam.api.key=YOUR_STEAM_API_KEY

Run Backend
cd backend
./mvnw spring-boot:run


Backend runs on:

http://localhost:8080

🎨 Frontend Setup (React + Vite)
Prerequisites

Node.js 18+

Install & Run
cd frontend
npm install
npm run dev


Frontend runs on:

http://localhost:5173

🔗 API Endpoints
Games
Method	Endpoint	Description
GET	/games	Get all games
POST	/games	Add new game
PUT	/games/{id}	Update game
DELETE	/games/{id}	Delete game
Steam
Method	Endpoint	Description
GET	/steam/games/{steamId}	Fetch owned Steam games
POST	/steam/import/{steamId}/{appId}	Import a game
🧠 Tech Decisions

DTOs to separate API models from entities

Enums for game status (type safety)

Global exception handling

Repository method naming (Spring Data JPA)

Single Git repository for full-stack project

🚀 Future Improvements

🔐 Authentication (JWT)

📊 Dashboard & charts

🎯 Filters & sorting

🐳 Docker support

☁ Deployment (Render / Railway / AWS)

👨‍💻 Author

Nishant Karpe
Java Backend Developer | Spring Boot | React | MySQL