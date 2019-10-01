package com.brmcerqueira.discord.codbot.narrator

import com.brmcerqueira.discord.codbot.BotMessage
import com.brmcerqueira.discord.codbot.isCod

class NarratorBotMessage : BotMessage<NarratorDto>() {
    override fun buildMessage(dto: NarratorDto, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        stringBuffer.appendln(when {
            dto.modifier != null && !isCod -> "Ajustou a dificuldade para ${dto.modifier}."
            dto.modifier != null && isCod -> "Aplicou um modificador de ${dto.modifier}."
            dto.isChangeCurrentChannel -> "Ok! mandarei as mensagens para por aqui!"
            else -> "Não posso mandar as mensagens para por aqui!"
        })

        stringBuffer.append("```")
    }
}
