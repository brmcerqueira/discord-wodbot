FROM openjdk:11

COPY ./build/libs ./discord-wodbot

WORKDIR /discord-wodbot

EXPOSE 4100

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar discord-wodbot-1.0-SNAPSHOT-fat.jar"]