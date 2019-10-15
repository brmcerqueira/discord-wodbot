package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage

class InitiativeBotMessage : BotMessage<InitiativeDto>() {
    override fun buildMessage(dto: InitiativeDto, stringBuffer: StringBuffer) {
        val dice = InitiativeManager.addInitiativeItem(dto)

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< ${dto.amount} + $dice = ${dto.amount + dice} >")

        stringBuffer.append("```")

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}
