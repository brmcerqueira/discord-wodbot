package com.brmcerqueira.discord.codbot

import kotlin.random.Random

class DicePool(private var amount: Int, private var explosion: Int = 11, isCanceller: Boolean = false) {

    var successes: Int = 0
        private set

    val dices = ArrayList<Int>()
    var isCriticalFailure: Boolean = false
        private set

    init {
        if (explosion > 11) {
            explosion = 11
        }
        else if (explosion < 8){
            explosion = 8
        }

        if (amount <= 0) {
            amount = 0
            val dice = randomDice()
            if(dice == 10) {
                successes++
                amount++
            }
            else if(dice == 1) {
                isCriticalFailure = true
            }
            dices.add(dice)
        }

        for (x in 0..amount) {
            val dice = randomDice()
            if(dice >= 8) {
                successes++
                if(dice >= explosion) {
                    amount++
                }
            }
            else if(isCanceller && dice == 1) {
                successes--
            }
            dices.add(dice)
        }

        dices.sortDescending()
    }

    private fun randomDice() = Random.nextInt(1,10)
}