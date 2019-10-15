package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.Wod

class InitiativeBotMessage : BotMessage<InitiativeDto>() {
    override fun buildMessage(dto: InitiativeDto, stringBuffer: StringBuffer) {
        val dice = Wod.randomDice()

        val initiativeQueueItem =  InitiativeQueueItem(dto.userId, dto.amount,dto.amount + dice, dto.name)

        InitiativeManager.addInitiativeItem(initiativeQueueItem, dto.withoutPenalty, dto.actions)

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< ${dto.amount} + $dice = ${initiativeQueueItem.total} >")

        stringBuffer.append("```")

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}
