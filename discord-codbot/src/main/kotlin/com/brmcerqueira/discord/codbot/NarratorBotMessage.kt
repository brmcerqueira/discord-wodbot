package com.brmcerqueira.discord.codbot

class NarratorBotMessage : BotMessage<NarratorDto>() {
    override fun buildMessage(dto: NarratorDto, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        stringBuffer.appendln(when {
            dto.modifier != null -> "Aplicou um modificador de ${dto.modifier}."
            dto.isChangeCurrentChannel -> "Ok! mandarei as mensagens para por aqui!"
            else -> "Não posso mandar as mensagens para por aqui!"
        })

        stringBuffer.append("```")
    }
}
