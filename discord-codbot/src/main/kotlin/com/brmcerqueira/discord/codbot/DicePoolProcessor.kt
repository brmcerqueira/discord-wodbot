package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

class DicePoolProcessor : IProcessor {

    companion object {
        private val regex = "^%(?<isCanceller>!)?(?<dices>[1-99].)\\s*(\\*(?<explosion>0|8|9))?.*\$".toRegex()
    }

    override fun match(event: MessageCreateEvent): Boolean {
        return event.message.content.map { regex.matches(it) }.orElse(false)
    }

    override fun go(event: MessageCreateEvent): Flux<Unit> {
        val matchResult = regex.matchEntire(event.message.content.get())!!
        return Flux.just(event.message)
            .flatMap(Message::getChannel)
            .flatMap { channel ->
                val dicePool =  DicePool(
                        matchResult.groups["dices"]!!.value.toInt(),
                        if (matchResult.groups["explosion"] != null)
                            matchResult.groups["explosion"]!!.value.toInt()
                        else 10,
                        matchResult.groups["isCanceller"] != null
                )

                channel.createMessage("```md\n [${dicePool.dices.joinToString(", ")}](${dicePool.successes})\n < ${dicePool.isCriticalFailure} >```")
            }
            .map { Unit }
    }
}