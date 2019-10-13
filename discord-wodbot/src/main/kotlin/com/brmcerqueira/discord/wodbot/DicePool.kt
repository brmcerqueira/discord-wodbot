package com.brmcerqueira.discord.wodbot

class DicePool(private val amount: Int,
               private val difficulty: Int,
               private val explosion: Int,
               private val isCanceller: Boolean,
               private val modifier: Int? = null) {

    var successes: Int = 0
        private set

    val successDices = ArrayList<Int>()
    val failureDices = ArrayList<Int>()
    var isCriticalFailure: Boolean = false
        private set

    init {
        val expl = when {
            explosion > 11 || explosion == 0 -> 11
            explosion < 8 -> 8
            else -> explosion
        }

        val total = if (modifier != null)
            amount + modifier
            else amount

        var amount = when {
            total > 99 -> 99
            total < 0 -> 0
            else -> total
        }

        var margin = 0

        var i = 1
        while (i <= amount) {
            val dice = randomDice()
            if(dice >= difficulty) {
                successes++

                if(dice >= expl) {
                    amount++
                    margin++
                }

                isCriticalFailure = false

                successDices.add(dice)
            }
            else {
                if(isCanceller && dice == 1) {
                    successes--
                    if (margin > 0) {
                        margin--
                        amount--
                    }

                    if (successDices.isEmpty()) {
                        isCriticalFailure = true
                    }
                }
                failureDices.add(dice)
            }
            i++
        }

        successDices.sortDescending()
        failureDices.sortDescending()
    }
}