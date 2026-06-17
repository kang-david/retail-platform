# Commands:
    
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