# discord-wodbot

``docker build --no-cache -t discord-wodbot:latest --build-arg PROJECT_TOKEN=<VALUE> .``

``docker run --name discord-wodbot -it -d -p 8085:8080 discord-codbot``

``docker rm discord-wodbot``

``docker logs discord-wodbot -f``

``docker tag discord-wodbot registry.heroku.com/discord-wodbot/latest``

``docker push registry.heroku.com/discord-wodbot/latest``

``heroku container:push web --arg PROJECT_TOKEN=<VALUE>``

``\@<Username>#Discriminator``