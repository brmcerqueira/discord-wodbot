package com.brmcerqueira.discord.codbot

class DicePool(private var amount: Int, private var explosion: Int, isCanceller: Boolean) {

    var successes: Int = 0
        private set

    val successDices = ArrayList<Int>()
    val failureDices = ArrayList<Int>()
    var isCriticalFailure: Boolean = false
        private set

    init {
        if (explosion > 11 || explosion == 0) {
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
                successDices.add(dice)
            }
            else {
                if(dice == 1) {
                    isCriticalFailure = true
                }
                failureDices.add(dice)
            }
        }

        for (x in 0 until amount) {
            val dice = randomDice()
            if(dice >= 8) {
                successes++
                if(dice >= explosion) {
                    amount++
                }
                successDices.add(dice)
            }
            else {
                if(isCanceller && dice == 1) {
                    successes--
                }
                failureDices.add(dice)
            }
        }

        successDices.sortDescending()
        failureDices.sortDescending()
    }
}