package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

class DicePoolProcessor : IProcessor {

    companion object {
        private val regex = "^%(?<isCanceller>!)?(?<dices>[1-9]?\\d)\\s*(\\*(?<explosion>0|8|9))?.*\$".toRegex()
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

                val stringBuffer = StringBuffer()

                stringBuffer.appendln("```md")

                stringBuffer.appendln("[${dicePool.successDices.format()}](${dicePool.failureDices.format()})")

                stringBuffer.appendln("# ${dicePool.successes}")

                if (dicePool.isCriticalFailure) {
                    stringBuffer.appendln("/* Critical Failure *")
                }

                stringBuffer.append("```")

                channel.createMessage(stringBuffer.toString())
            }
            .map { Unit }
    }

    private fun ArrayList<Int>.format(): String =  if (this.isEmpty()) "-" else this.joinToString(" - ")
}