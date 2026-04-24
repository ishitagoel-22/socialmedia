# Social Media Backend

## Tech Stack
- Spring Boot
- PostgreSQL
- Redis (partially implemented)

---

## Features

### Phase 1 (Completed)
- Create Post API
- Add Comment API
- Like Post API

---

## API Endpoints

- POST /api/posts
- POST /api/posts/{postId}/comments
- POST /api/posts/{postId}/like

---

## Database
- PostgreSQL used for storing posts and comments

---

## Redis (Phase 2 - Partial)
- Attempted virality score and cooldown logic
- Could not fully complete due to setup issues

---

## Docker
- docker-compose.yml included

---

## Run Project
mvn spring-boot:run