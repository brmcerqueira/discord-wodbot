FROM openjdk:11


ARG PROJECT_TOKEN=""
ENV PROJECT_TOKEN=${PROJECT_TOKEN}

COPY ./build/libs ./discord-codbot

WORKDIR /discord-codbot

EXPOSE 8080

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar discord-codbot-1.0-SNAPSHOT-fat.jar $PROJECT_TOKEN"]