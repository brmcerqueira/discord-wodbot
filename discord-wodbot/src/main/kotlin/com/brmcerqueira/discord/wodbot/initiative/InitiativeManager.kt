package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.Wod
import discord4j.core.`object`.util.Snowflake
import java.util.*
import kotlin.Comparator

object InitiativeManager {
    private val initiativeComparator = Comparator<InitiativeItem> { left, right ->
        when {
            left.index > right.index -> 1
            left.index < right.index -> -1
            else -> when {
                left.initiative > right.initiative -> -1
                left.initiative < right.initiative -> 1
                else -> when {
                    left.total > right.total -> -1
                    left.total < right.total -> 1
                    else -> 0
                }
            }
        }
    }

    private var indexInitiativeQueue = 1
    private var indexCharacter = 1
    private val sceneInitiativeQueue = PriorityQueue(initiativeComparator)
    private var initiativeQueue = PriorityQueue(initiativeComparator)

    fun printInitiativeQueue(stringBuffer: StringBuffer) {
        if (initiativeQueue.isNotEmpty()) {
            stringBuffer.appendln("__***Resumo***__ -> $indexInitiativeQueue")

            val queue = PriorityQueue(initiativeQueue)

            var index = 1
            while (queue.peek() != null)
            {
                val item = queue.poll()
                stringBuffer.append("$index. <@${item.userId.asString()}>")
                if(item.name != null) {
                    stringBuffer.append(" (${item.name.trim()})")
                }
                stringBuffer.append(" #${item.characterId} -> ${item.total}")
                if (item.penalty != null) {
                    stringBuffer.appendln(" | Penalidade: ${item.penalty}")
                }
                else {
                    stringBuffer.appendln()
                }
                index++
            }
        }
    }

    private fun PriorityQueue<InitiativeItem>.addActions(dto: InitiativeDto, userId: Snowflake, characterId: Int, dice: Int) {
        val initiativeQueueItem =  InitiativeItem(userId, characterId, 1, dto.amount,dto.amount + dice, dto.name,
            if (!dto.withoutPenalty && dto.actions != null) -dto.actions else null)
        this.add(initiativeQueueItem)
        if (dto.actions != null) {
            for (index in 2..dto.actions + 1) {
                this.add(initiativeQueueItem.copy(index = index,
                        penalty = if (dto.withoutPenalty) null else -(dto.actions + index - 1)))
            }
        }
    }

    private fun PriorityQueue<InitiativeItem>.remove(userId: Snowflake?, characterId: Int?, amount: Int?) {
        var total = 0
        this.removeIf {
            when {
                amount != null && amount > total && characterId != null && it.characterId == characterId -> {
                    total++
                    true
                }
                amount != null && amount > total && userId != null && it.userId == userId ->  {
                    total++
                    true
                }
                amount == null && characterId != null && it.characterId == characterId -> true
                amount == null && userId != null && it.userId == userId -> true
                else -> false
            }
        }
    }

    fun add(dto: InitiativeDto, userId: Snowflake): Int {
        val dice = Wod.randomDice()
        sceneInitiativeQueue.addActions(dto, userId, indexCharacter, dice)
        initiativeQueue.addActions(dto, userId, indexCharacter, dice)
        indexCharacter++
        return dice
    }

    fun clear() {
        indexInitiativeQueue = 1
        indexCharacter = 1
        sceneInitiativeQueue.clear()
        initiativeQueue.clear()
    }

    fun checkRestart() {
        if (initiativeQueue.isEmpty()) {
            restart()
        }
    }

    fun restart() {
        initiativeQueue = PriorityQueue(sceneInitiativeQueue)
    }

    fun remove(onlyCurrent: Boolean, userId: Snowflake? = null, characterId: Int? = null, amount: Int? = null) {
        if (!onlyCurrent) {
            sceneInitiativeQueue.remove(userId, characterId, amount)
        }
        initiativeQueue.remove(userId, characterId, amount)
    }
}