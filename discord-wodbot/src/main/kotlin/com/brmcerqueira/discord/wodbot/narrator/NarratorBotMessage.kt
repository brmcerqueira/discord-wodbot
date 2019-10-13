package com.brmcerqueira.discord.wodbot.narrator

import com.brmcerqueira.discord.wodbot.BotMessage

class NarratorBotMessage : BotMessage<NarratorDto>() {
    override fun buildMessage(dto: NarratorDto, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        stringBuffer.appendln(when {
            dto.modifier != null -> "Ajustou a dificuldade para ${dto.modifier}."
            dto.isChangeCurrentChannel -> "Ok! mandarei as mensagens para por aqui!"
            else -> "Não posso mandar as mensagens para por aqui!"
        })

        stringBuffer.append("```")
    }
}
