# discord-codbot

``docker build --no-cache -t discord-codbot:latest --build-arg TOKEN=<VALUE> .``

``docker run --name discord-codbot -it -d -p 8085:8080 discord-codbot``

``docker rm discord-codbot``

``docker logs discord-codbot -f``

``docker exec -it discord-codbot bash``