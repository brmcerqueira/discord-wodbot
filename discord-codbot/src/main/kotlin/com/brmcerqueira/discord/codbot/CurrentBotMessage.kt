package com.brmcerqueira.discord.codbot

class CurrentBotMessage : BotMessage<Boolean>() {
    override fun buildMessage(dto: Boolean, stringBuffer: StringBuffer) {
        stringBuffer.appendln("```fix")

        stringBuffer.appendln(if (dto) "Estou de olho em você!" else "Não posso olhar você por aqui!")

        stringBuffer.append("```")
    }
}
