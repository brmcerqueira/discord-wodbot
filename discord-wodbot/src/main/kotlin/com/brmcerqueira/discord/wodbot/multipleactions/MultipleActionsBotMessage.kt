package com.brmcerqueira.discord.wodbot.multipleactions

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.initiative.InitiativeManager
import discord4j.core.`object`.util.Snowflake

class MultipleActionsBotMessage : BotMessage<MultipleActionsDto>() {
    override fun buildMessage(dto: MultipleActionsDto, userId: Snowflake?, stringBuffer: StringBuffer) {
        InitiativeManager.adjustMultipleActions(dto, userId!!)

        stringBuffer.appendln("```md")
        stringBuffer.append("[ Ações: ")
        stringBuffer.append(dto.actions)
        stringBuffer.append(" ]")

        if (dto.characterId != null) {
            stringBuffer.append("( Personagem: ")
            stringBuffer.append(dto.characterId)
            stringBuffer.appendln(" )")
        }
        else {
            stringBuffer.appendln()
        }

        if (dto.withoutPenalty) {
            stringBuffer.appendln("> As penalidades não foram aplicadas.")
        }

        stringBuffer.append("```")

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}