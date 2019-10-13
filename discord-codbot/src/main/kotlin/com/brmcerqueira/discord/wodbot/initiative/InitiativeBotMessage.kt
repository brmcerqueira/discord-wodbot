package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.randomDice

class InitiativeBotMessage : BotMessage<Int>() {
    override fun buildMessage(dto: Int, stringBuffer: StringBuffer) {
        val dice = randomDice()

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< $dto + $dice = ${dto + dice} >")

        stringBuffer.append("```")
    }
}
