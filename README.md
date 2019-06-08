# discord-codbot

``docker build --no-cache -t discord-codbot:latest --build-arg PROJECT_TOKEN=<VALUE> .``

``docker run --name discord-codbot -it -d -p 8085:8080 discord-codbot``

``docker rm discord-codbot``

``docker logs discord-codbot -f``

``docker tag discord-codbot registry.heroku.com/discord-codbot/latest``

``docker push registry.heroku.com/discord-codbot/latest``

``heroku container:push web --arg PROJECT_TOKEN=<VALUE>``
