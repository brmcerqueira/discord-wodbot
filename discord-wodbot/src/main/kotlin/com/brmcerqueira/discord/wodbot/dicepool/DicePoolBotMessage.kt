package com.brmcerqueira.discord.wodbot.dicepool

import com.brmcerqueira.discord.wodbot.BotMessage
import com.brmcerqueira.discord.wodbot.DicePool
import com.brmcerqueira.discord.wodbot.Wod
import com.brmcerqueira.discord.wodbot.format

class DicePoolBotMessage : BotMessage<DicePoolDto>() {
    override fun buildMessage(dto: DicePoolDto, stringBuffer: StringBuffer) {
        val difficulty = Wod.difficulty ?: dto.difficulty

        if (Wod.difficulty != null) {
            Wod.difficulty = null
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

        stringBuffer.appendln(when {
            dicePool.isCriticalFailure -> "/* Falha Crítica *"
            dicePool.successes <= 0 -> "< Falha >"
            else -> "< Sucessos = ${dicePool.successes} >"
        })

        stringBuffer.append("```")
    }
}