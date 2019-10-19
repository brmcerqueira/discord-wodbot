package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.LayoutTable
import com.brmcerqueira.discord.wodbot.PenaltyMode
import com.brmcerqueira.discord.wodbot.Wod
import com.brmcerqueira.discord.wodbot.multipleactions.MultipleActionsDto
import com.brmcerqueira.discord.wodbot.not
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
            stringBuffer.appendln("```xml")
            stringBuffer.appendln("< Fila = $indexInitiativeQueue >")
            stringBuffer.appendln("```")

            val queue = PriorityQueue(initiativeQueue)
            val layoutTable = LayoutTable(10)

            var index = 1
            while (queue.peek() != null)
            {
                val item = queue.poll()
                layoutTable.row("$index.",
                    item.name,
                    "Id: ${item.characterId}",
                    if (item.penalty != null) "Penalidade: ${item.penalty}" else null,
                    "Total: ${item.total}",
                    !"<@${item.userId.asString()}>"
                )
                index++
            }

            layoutTable.print(stringBuffer)
        }
    }

    private fun PriorityQueue<InitiativeItem>.addExtraActions(initiativeItem: InitiativeItem, actions: Int, penaltyMode: PenaltyMode) {
        if (penaltyMode == PenaltyMode.Offensive) {
            initiativeItem.penalty = -(actions + 1)
        }

        for (index in 2..actions + 1) {
            this.add(initiativeItem.copy(
                index = index,
                penalty = when(penaltyMode) {
                    PenaltyMode.Defensive -> -(index - 1)
                    PenaltyMode.Offensive -> -(index + actions)
                    else -> null
                }))
        }
    }

    private fun PriorityQueue<InitiativeItem>.remove(userId: Snowflake?, characterId: Int?, amount: Int?) {
        var total = 0
        this.removeIf {
            when {
                amount != null && amount > total && ((userId != null && userId == it.userId) || (characterId != null && characterId == it.characterId)) -> {
                    total++
                    true
                }
                amount == null && ((userId != null && userId == it.userId) || (characterId != null && characterId == it.characterId)) -> true
                else -> false
            }
        }
    }

    fun adjustMultipleActions(dto: MultipleActionsDto, userId: Snowflake) {
        initiativeQueue.removeIf {
            userId == it.userId && it.index > 1 && (dto.characterId == null || dto.characterId == it.characterId)
        }

        val initiativeItem = initiativeQueue.firstOrNull {
            userId == it.userId && (dto.characterId == null || dto.characterId == it.characterId)
        }

        if (initiativeItem != null) {
            if (dto.actions > 0) {
                initiativeQueue.addExtraActions(initiativeItem, dto.actions, dto.penaltyMode)
            }
            else {
                initiativeItem.penalty = null
            }
        }
    }

    fun add(dto: InitiativeDto, userId: Snowflake): Int {
        val dice = Wod.randomDice()

        if (Wod.narratorId != userId) {
            sceneInitiativeQueue.removeIf {
                userId == it.userId
            }
            initiativeQueue.removeIf {
                userId == it.userId
            }
        }

        val sceneInitiativeItem = InitiativeItem(userId, indexCharacter, 1, dto.amount,dto.amount + dice, dto.name)
        sceneInitiativeQueue.add(sceneInitiativeItem)
        val initiativeItem =  sceneInitiativeItem.copy()
        initiativeQueue.add(initiativeItem)
        if (dto.actions != null) {
            initiativeQueue.addExtraActions(initiativeItem, dto.actions, dto.penaltyMode)
        }
        indexCharacter++
        return dice
    }

    fun getPenalty(userId: Snowflake): Int? = initiativeQueue.firstOrNull { it.userId == userId }?.penalty

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
        indexInitiativeQueue++
        initiativeQueue = PriorityQueue(sceneInitiativeQueue)
    }

    fun remove(onlyCurrent: Boolean, userId: Snowflake? = null, characterId: Int? = null, amount: Int? = null) {
        if (!onlyCurrent) {
            sceneInitiativeQueue.remove(userId, characterId, amount)
        }
        initiativeQueue.remove(userId, characterId, amount)
    }
}