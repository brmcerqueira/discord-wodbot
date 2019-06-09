package com.brmcerqueira.discord.codbot

class InitiativeBotMessage : BotMessage<Int>() {
    override fun buildMessage(dto: Int, stringBuffer: StringBuffer) {
        val dice = randomDice()

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< $dto + $dice = ${dto + dice} >")

        stringBuffer.append("```")
    }
}
