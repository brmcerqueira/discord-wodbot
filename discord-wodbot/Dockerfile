FROM openjdk:11

ARG PROJECT_TOKEN=""
ENV PROJECT_TOKEN=${PROJECT_TOKEN}

COPY ./build/libs ./discord-wodbot

WORKDIR /discord-wodbot

EXPOSE 4100

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar discord-wodbot-1.0-SNAPSHOT-fat.jar $PROJECT_TOKEN"]