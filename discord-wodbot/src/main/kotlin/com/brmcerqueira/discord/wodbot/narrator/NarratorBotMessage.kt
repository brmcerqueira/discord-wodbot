package com.brmcerqueira.discord.wodbot.narrator

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.Wod
import com.brmcerqueira.discord.wodbot.initiative.InitiativeManager
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class NarratorBotMessage : BotMessage<Pair<MatchResult, MessageChannel>>() {

    private val snowflakeRegex = "^<@!?(?<id>\\d*)>\$".toRegex()

    override fun buildMessage(dto: Pair<MatchResult, MessageChannel>, userId: Snowflake?, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        var mustPrintInitiativeQueue = false

        if (Wod.narratorId != userId) {
            stringBuffer.appendln("Você não é o narrador!")
        }
        else {
            val command = dto.first.groups["command"]?.value

            val arguments = dto.first.groups["arguments"]?.value?.split(' ')?.filter { it.isNotEmpty() }

            stringBuffer.appendln(when(command) {
                "dif" -> {
                    when(val value = arguments.getArg(0)?.toIntOrNull()) {
                        in 3..9 -> {
                            Wod.difficulty = value
                            "O narrador ajustou a dificuldade para ${Wod.difficulty}."
                        }
                        !in 3..9 -> "A dificuldade deve ser maior igual a '3' e menor igual a '9'. -> $value"
                        else -> "O comando 'dif' não tem argumentos válidos. -> $arguments"
                    }
                }
                "init" -> {
                    when(arguments.getArg(0)) {
                        "remove" -> {
                            val id = arguments.getArg(1)
                            val result = id?.let { snowflakeRegex.matchEntire(it) }
                            InitiativeManager.remove(false,
                                    userId =  if (result != null && result.groups["id"] != null)
                                        Snowflake.of(result.groups["id"]!!.value)
                                    else null,
                                    characterId = id?.toIntOrNull(),
                                    amount =  when(arguments.getArg(2)?.toIntOrNull()) {
                                        0 -> null
                                        in 1..99 -> arguments.getArg(2)?.toIntOrNull()
                                        else -> 1
                                    })
                            mustPrintInitiativeQueue = true
                            "O narrador removeu o item."
                        }
                        "restart" -> {
                            InitiativeManager.restart()
                            mustPrintInitiativeQueue = true
                            "Ok! Fila de iniciativa reinicializada!"
                        }
                        "clear" -> {
                            InitiativeManager.clear()
                            "Ok! Fila de iniciativa vazia!"
                        }
                        else -> "O comando 'init' não tem argumentos válidos. -> $arguments"
                    }
                }
                "here" -> {
                    Wod.messageChannel = dto.second
                    "Ok! mandarei as mensagens para por aqui!"
                }
                else -> "Não sei oque é '$command'."
            })
        }

        stringBuffer.append("```")

        if (mustPrintInitiativeQueue) {
            InitiativeManager.printInitiativeQueue(stringBuffer)
        }
    }

    private fun List<String>?.getArg(index: Int): String? =
        if (this != null && this.count() > index)
            this[index]
        else null
}
