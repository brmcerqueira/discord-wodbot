package com.brmcerqueira.discord.codbot

class InitiativeBotMessage : BotMessage<Int>() {
    override fun buildMessage(initiative: Int, stringBuffer: StringBuffer) {
        val dice = randomDice()

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< $initiative + $dice = ${initiative + dice} >")

        stringBuffer.append("```")
    }
}
