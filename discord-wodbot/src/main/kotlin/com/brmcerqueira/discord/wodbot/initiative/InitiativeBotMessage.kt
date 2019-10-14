package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.initiativeQueue
import com.brmcerqueira.discord.wodbot.randomDice
import java.util.*

class InitiativeBotMessage : BotMessage<InitiativeDto>() {
    override fun buildMessage(dto: InitiativeDto, stringBuffer: StringBuffer) {
        val dice = randomDice()

        val initiativeQueueItem =  InitiativeQueueItem(dto.userId,dto.amount + dice, dto.name)

        initiativeQueue.add(initiativeQueueItem)

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< ${dto.amount} + $dice = ${initiativeQueueItem.amount} >")

        stringBuffer.append("```")

        stringBuffer.appendln("__***Resumo***__")

        val queue = PriorityQueue(initiativeQueue)

        var index = 1
        while (queue.peek() != null)
        {
            val item = queue.poll()
            stringBuffer.append("$index. <@${item.userId.asString()}>")
            if(item.name != null) {
                stringBuffer.append("(${item.name})")
            }
            stringBuffer.appendln(" -> ${item.amount}")
            index++
        }
    }
}
