package com.brmcerqueira.discord.codbot


class InitiativeProcessor : ReplyProcessor() {
    override fun getRegex(): Regex = "^\\$(?<initiative>[1-9]?\\d)\\s*(?<description>.*)\$".toRegex()

    override fun buildMessage(matchResult: MatchResult, stringBuffer: StringBuffer) {
        val initiative = matchResult.groups["initiative"]!!.value.toInt()

        val dice = randomDice()

        stringBuffer.appendln("```md")

        stringBuffer.appendln("< $initiative + $dice = ${initiative + dice} >")

        stringBuffer.append("```")
    }
}