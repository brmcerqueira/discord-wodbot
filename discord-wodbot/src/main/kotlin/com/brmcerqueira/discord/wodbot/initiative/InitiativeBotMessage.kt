package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage
import discord4j.core.`object`.util.Snowflake

class InitiativeBotMessage : BotMessage<InitiativeDto>() {
    override fun buildMessage(dto: InitiativeDto, userId: Snowflake?, stringBuffer: StringBuffer) {
        val dice = InitiativeManager.add(dto, userId!!)

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< ${dto.amount} + $dice = ${dto.amount + dice} >")

        stringBuffer.append("```")

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}
