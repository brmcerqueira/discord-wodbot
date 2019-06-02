package com.brmcerqueira.discord.codbot

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.DiscordClientBuilder
import discord4j.core.`object`.entity.Message

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client = DiscordClientBuilder("token").build()

            client.eventDispatcher.on(ReadyEvent::class.java)
                    .subscribe { ready -> println("Logged in as " + ready.self.username) }

            client.eventDispatcher.on(MessageCreateEvent::class.java)
                    .map<Message> { it.message }
                    .filter { msg -> msg.content.map{ "!ping" == it }.orElse(false) }
                    .flatMap(Message::getChannel)
                    .flatMap { channel -> channel.createMessage("Pong!") }
                    .subscribe()

            client.login().block()
        }
    }
}