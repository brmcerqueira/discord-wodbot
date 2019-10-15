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

        val command = dto.first.groups["command"]?.value

        val arguments = dto.first.groups["arguments"]?.value?.split(' ')?.filter { it.isNotEmpty() }

        var mustPrintInitiativeQueue = false

        stringBuffer.appendln(when(command) {
            "dif" -> {
                if (arguments != null && arguments.isNotEmpty() && arguments[0].toIntOrNull() != null) {
                    val value = arguments[0].toInt()
                    if (value in 3..9) {
                        Wod.difficulty = value
                        "O narrador ajustou a dificuldade para ${Wod.difficulty}."
                    }
                    else "A dificuldade deve ser maior igual a '3' e menor igual a '9'. -> $value"
                }
                else "O comando 'dif' não tem argumentos válidos. -> $arguments"
            }
            "remove" -> {
                if (arguments != null && arguments.isNotEmpty()) {
                    val result = snowflakeRegex.matchEntire(arguments[0])
                    InitiativeManager.remove(false,
                        userId =  if (result != null && result.groups["id"] != null)
                            Snowflake.of(result.groups["id"]!!.value)
                        else null,
                        characterId = arguments[0].toIntOrNull(),
                        amount = if (arguments.count() == 2) when(arguments[1].toIntOrNull()) {
                            0 -> null
                            in 1..99 -> arguments[1].toIntOrNull()
                            else -> 1
                        } else 1)
                    mustPrintInitiativeQueue = true
                    "O narrador removeu o item."
                }
                else "O comando 'remove' não tem argumentos válidos. -> $arguments"
            }
            "reset" -> {
                InitiativeManager.clear()
                "Ok! Fila de iniciativa vazia!"
            }
            "here" -> {
                Wod.messageChannel = dto.second
                "Ok! mandarei as mensagens para por aqui!"
            }
            else -> "Não sei oque é '$command'."
        })

        stringBuffer.append("```")

        if (mustPrintInitiativeQueue) {
            InitiativeManager.printInitiativeQueue(stringBuffer)
        }
    }
}
