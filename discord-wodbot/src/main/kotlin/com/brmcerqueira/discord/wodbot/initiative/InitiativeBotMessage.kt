package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage
import discord4j.core.`object`.util.Snowflake

class InitiativeBotMessage : BotMessage<InitiativeDto>() {
    override fun buildMessage(dto: InitiativeDto, userId: Snowflake?, stringBuffer: StringBuffer) {
        val dice = InitiativeManager.add(dto, userId!!)

        stringBuffer.appendln("```md")

        if (dto.actions != null) {
            stringBuffer.append("[ Ações: ")
            stringBuffer.append(dto.actions + 1)
            stringBuffer.append(" ]( Modo de Penalidade: ")
            stringBuffer.append(dto.penaltyMode)
            stringBuffer.appendln(" )")
        }

        stringBuffer.appendln("< ${dto.amount} + $dice = ${dto.amount + dice} >")

        stringBuffer.append("```")

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}
