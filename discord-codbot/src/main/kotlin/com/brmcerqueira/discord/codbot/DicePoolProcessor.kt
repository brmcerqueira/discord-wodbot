package com.brmcerqueira.discord.codbot

class DicePoolProcessor : ReplyProcessor() {
    override fun getRegex(): Regex = "^%(?<isCanceller>!)?(?<dices>[1-9]?\\d)\\s*(\\*(?<explosion>0|8|9))?(?<description>.*)\$".toRegex()

    override fun buildMessage(matchResult: MatchResult, stringBuffer: StringBuffer) {
        val dicePool =  DicePool(
            matchResult.groups["dices"]!!.value.toInt(),
            if (matchResult.groups["explosion"] != null)
                matchResult.groups["explosion"]!!.value.toInt()
            else 10,
            matchResult.groups["isCanceller"] != null
        )

        stringBuffer.appendln("```md")

        stringBuffer.appendln("[${dicePool.successDices.format()}](${dicePool.failureDices.format()})")

        stringBuffer.appendln("# ${dicePool.successes}")

        if (dicePool.isCriticalFailure) {
            stringBuffer.appendln("/* Critical Failure *")
        }

        stringBuffer.append("```")
    }

    private fun ArrayList<Int>.format(): String =  if (this.isEmpty()) "-" else this.joinToString(" - ")
}