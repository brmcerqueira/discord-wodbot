package com.brmcerqueira.discord.wodbot.dicepool

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.DicePool
import com.brmcerqueira.discord.wodbot.Wod
import com.brmcerqueira.discord.wodbot.format
import com.brmcerqueira.discord.wodbot.initiative.InitiativeManager
import discord4j.core.`object`.util.Snowflake
import kotlin.math.absoluteValue

class DicePoolBotMessage : BotMessage<DicePoolDto>() {
    override fun buildMessage(dto: DicePoolDto, userId: Snowflake?, stringBuffer: StringBuffer) {
        val difficulty = Wod.difficulty ?: dto.difficulty

        if (Wod.difficulty != null) {
            Wod.difficulty = null
        }

        var amount = dto.amount

        val penalty = InitiativeManager.getPenalty(userId!!)

        if (penalty != null) {
            amount += penalty
        }

        val dicePool = DicePool(amount, difficulty, if (dto.isSpecialization) 10 else 0, dto.isCanceller)

        stringBuffer.appendln("```md")
        stringBuffer.append("[ Dados: ")

        if (penalty != null) {
            stringBuffer.append(dto.amount)
            stringBuffer.append(" - ")
            stringBuffer.append(penalty.absoluteValue)
            stringBuffer.append(" = ")
        }

        stringBuffer.append(amount)
        stringBuffer.append(" ]( Dificuldade: ")
        stringBuffer.append(difficulty)
        stringBuffer.appendln(" )")

        if (dto.isSpecialization) {
            stringBuffer.appendln("/* Com Especialização *")
        }

        if (dto.isCanceller) {
            stringBuffer.appendln("> O '1' cancela sucesso.")
        }

        stringBuffer.appendln("# Resultado")
        stringBuffer.appendln("[ ${dicePool.successDices.format()} ][ ${dicePool.failureDices.format()} ]")

        stringBuffer.appendln(when {
            dicePool.isCriticalFailure -> "/* Falha Crítica *"
            dicePool.successes <= 0 -> "< Falha >"
            else -> "< Sucessos = ${dicePool.successes} >"
        })

        stringBuffer.append("```")

        InitiativeManager.remove(true, userId, null, 1)

        InitiativeManager.checkRestart()

        InitiativeManager.printInitiativeQueue(stringBuffer)
    }
}