package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.randomDice

class InitiativeBotMessage : BotMessage<InitiativeDto>() {
    override fun buildMessage(dto: InitiativeDto, stringBuffer: StringBuffer) {
        val dice = randomDice()

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< ${dto.amount} + $dice = ${dto.amount + dice} >")

        stringBuffer.append("```")
    }
}
