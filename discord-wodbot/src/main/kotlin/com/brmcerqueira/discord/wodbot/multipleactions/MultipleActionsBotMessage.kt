package com.brmcerqueira.discord.wodbot.multipleactions

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.initiative.InitiativeManager
import discord4j.core.`object`.util.Snowflake

class MultipleActionsBotMessage : BotMessage<MultipleActionsDto>() {
    override fun buildMessage(dto: MultipleActionsDto, userId: Snowflake?, stringBuffer: StringBuffer) {
        InitiativeManager.adjustMultipleActions(dto, userId!!)

        stringBuffer.appendln("```md")
        stringBuffer.append("[ Ações: ")
        stringBuffer.append(dto.actions + 1)
        stringBuffer.append(" ]( Modo de Penalidade: ")
        stringBuffer.append(dto.penaltyMode)
        stringBuffer.appendln(" )")

        if (dto.characterId != null) {
            stringBuffer.appendln("< Personagem = ${dto.characterId} >")
        }

        stringBuffer.append("```")

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}