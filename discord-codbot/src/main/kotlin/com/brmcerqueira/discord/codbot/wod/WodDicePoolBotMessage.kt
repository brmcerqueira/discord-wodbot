package com.brmcerqueira.discord.codbot.wod

import com.brmcerqueira.discord.codbot.BotMessage
import com.brmcerqueira.discord.codbot.DicePool
import com.brmcerqueira.discord.codbot.format
import com.brmcerqueira.discord.codbot.modifier

class WodDicePoolBotMessage : BotMessage<WodDicePoolDto>() {
    override fun buildMessage(dto: WodDicePoolDto, stringBuffer: StringBuffer) {
        val difficulty = modifier ?: dto.difficulty

        if (modifier != null) {
            modifier = null
        }

        val dicePool = DicePool(dto.amount, difficulty, if (dto.isSpecialization) 10 else 0, dto.isCanceller)

        stringBuffer.appendln("```md")
        stringBuffer.append("[ Dados: ")
        stringBuffer.append(dto.amount)
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

        if (dicePool.isCriticalFailure) {
            stringBuffer.appendln("/* Falha Crítica *")
        }
        else {
            stringBuffer.appendln("< Sucessos = ${dicePool.successes} >")
        }

        stringBuffer.append("```")
    }
}