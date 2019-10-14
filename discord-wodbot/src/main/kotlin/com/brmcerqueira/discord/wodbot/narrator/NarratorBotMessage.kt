package com.brmcerqueira.discord.wodbot.narrator

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.messageChannel
import com.brmcerqueira.discord.wodbot.difficulty
import com.brmcerqueira.discord.wodbot.initiativeQueue
import discord4j.core.`object`.entity.MessageChannel

class NarratorBotMessage : BotMessage<Pair<MatchResult, MessageChannel>>() {
    override fun buildMessage(dto: Pair<MatchResult, MessageChannel>, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        val command = dto.first.groups["command"]?.value

        val arguments = dto.first.groups["arguments"]?.value?.split(' ')?.filter { it.isNotEmpty() }

        stringBuffer.appendln(when(command) {
            "dif" -> {
                if (arguments != null && arguments.isNotEmpty() && arguments[0].toIntOrNull() != null) {
                    val value = arguments[0].toInt()
                    if (value in 3..9) {
                        difficulty = value
                        "O narrador ajustou a dificuldade para $difficulty."
                    }
                    else "A dificuldade deve ser maior igual a '3' e menor igual a '9'. -> $value"
                }
                else "O comando 'dif' não tem argumentos válidos. -> $arguments"
            }
            "reset" -> {
                initiativeQueue.clear()
                "Ok! Fila de iniciativa vazia!"
            }
            "here" -> {
                messageChannel = dto.second
                "Ok! mandarei as mensagens para por aqui!"
            }
            else -> "Não sei oque é '$command'."
        })

        stringBuffer.append("```")
    }
}
