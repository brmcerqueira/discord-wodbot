package com.brmcerqueira.discord.codbot

class DicePoolBotMessage : BotMessage<DicePoolDto>() {
    override fun buildMessage(dto: DicePoolDto, stringBuffer: StringBuffer) {
        val dicePool = DicePool(dto)

        stringBuffer.appendln("```md")
        stringBuffer.append("[ Dados = ${dto.amount} ]")

        if (dto.explosion in 8..10) {
            stringBuffer.appendln("( Explosão = ${dto.explosion} )")
        }
        else {
            stringBuffer.appendln("( Sem explosão )")
        }

        if (dto.isCanceller) {
            stringBuffer.appendln("> O '1' cancela sucesso.")
        }

        stringBuffer.appendln("# Resultado")
        stringBuffer.appendln("[${dicePool.successDices.format()}][${dicePool.failureDices.format()}]")

        if (dicePool.isCriticalFailure) {
            stringBuffer.appendln("/* Falha Crítica *")
        }
        else {
            stringBuffer.appendln("< Sucessos = ${dicePool.successes} >")
        }

        stringBuffer.append("```")
    }

    private fun ArrayList<Int>.format(): String =  if (this.isEmpty()) "-" else this.joinToString(" - ")
}