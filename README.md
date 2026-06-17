# API Documentation:

[Swagger UI](http://localhost:8080/swagger-ui.html)

[Raw OpenAPI JSON](http://localhost:8080/v3/api-docs)

# Docker Commands:
    
Build & Start:

    docker compose up --build

Build:

    docker compose build

Start:

    docker compose up


Stop:

    docker compose down -v

Start (DB only with docker-compose, app on localhost; fast reloads ideal for development):

    docker compose up postgres

Set env variables for every session if running app on localhost:

    ## Check .env file for more information ##

# Git Workflow:

    git checkout dev
    git pull origin dev
    git checkout -b feature/feature-name
    
Work -> commit -> push:

    git push -u origin feature/feature-name

Then PR: ```feature/feature-name -> dev```

When dev is stable: ```dev -> master```