package com.brmcerqueira.discord.codbot.cod

import com.brmcerqueira.discord.codbot.DicePoolDto
import com.brmcerqueira.discord.codbot.randomDice

class CodDicePool(private val dicePoolDto: DicePoolDto, private val modifier: Int? = null) {

    var successes: Int = 0
        private set

    val successDices = ArrayList<Int>()
    val failureDices = ArrayList<Int>()
    var isCriticalFailure: Boolean = false
        private set

    init {
        val explosion = when {
            dicePoolDto.explosion > 11 || dicePoolDto.explosion == 0 -> 11
            dicePoolDto.explosion < 8 -> 8
            else -> dicePoolDto.explosion
        }

        val total = if (modifier != null)
            dicePoolDto.amount + modifier
            else dicePoolDto.amount

        var amount = when {
            total > 99 -> 99
            total < 0 -> 0
            else -> total
        }

        if (amount == 0) {
            amount = 0
            val dice = randomDice()
            if(dice == 10) {
                successes++
                amount++
                successDices.add(dice)
            }
            else {
                if(dice == 1) {
                    isCriticalFailure = true
                }
                failureDices.add(dice)
            }
        }

        var i = 1
        while (i <= amount) {
            val dice = randomDice()
            if(dice >= 8) {
                successes++
                if(dice >= explosion) {
                    amount++
                }
                successDices.add(dice)
            }
            else {
                if(dicePoolDto.isCanceller && dice == 1) {
                    successes--
                }
                failureDices.add(dice)
            }
            i++
        }

        successDices.sortDescending()
        failureDices.sortDescending()
    }
}