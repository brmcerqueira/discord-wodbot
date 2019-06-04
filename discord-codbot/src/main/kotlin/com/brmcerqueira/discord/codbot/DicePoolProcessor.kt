package com.brmcerqueira.discord.codbot

class DicePoolProcessor : ReplyProcessor() {
    override fun getRegex(): Regex = "^%(?<isCanceller>!)?(?<dices>[1-9]?\\d)\\s*(\\*(?<explosion>0|8|9))?(?<description>.*)\$".toRegex()

    override fun buildMessage(matchResult: MatchResult, stringBuffer: StringBuffer) {
        val dices = matchResult.groups["dices"]!!.value.toInt()

        val explosion = if (matchResult.groups["explosion"] != null)
            matchResult.groups["explosion"]!!.value.toInt()
        else 10

        val isCanceller = matchResult.groups["isCanceller"] != null

        val dicePool =  DicePool(dices, explosion, isCanceller)

        stringBuffer.appendln("```md")
        stringBuffer.append("[ Dados = $dices ]")

        if (explosion in 8..10) {
            stringBuffer.appendln("( Explosão = $explosion )")
        }
        else {
            stringBuffer.appendln("( Sem explosão )")
        }

        if (isCanceller) {
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