package com.brmcerqueira.discord.wodbot.narrator

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.messageChannel
import com.brmcerqueira.discord.wodbot.modifier
import discord4j.core.`object`.entity.MessageChannel

class NarratorBotMessage : BotMessage<Pair<MatchResult, MessageChannel>>() {
    override fun buildMessage(dto: Pair<MatchResult, MessageChannel>, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        val command = dto.first.groups["command"]?.value

        val arguments = dto.first.groups["arguments"]?.value?.split(' ')?.filter { it.isNotEmpty() }

        stringBuffer.appendln(when(command) {
            "dif" -> {
                if (arguments != null && arguments.isNotEmpty()
                        && arguments[0].toIntOrNull() != null && arguments[0].toIntOrNull() in 3..9) {
                    modifier = arguments[0].toInt()
                    "Ajustou a dificuldade para $modifier."
                }
                else "O comando 'dif' não tem argumentos válidos: $arguments"
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
