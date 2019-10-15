package com.brmcerqueira.discord.wodbot.initiative

import java.util.*
import kotlin.Comparator

object InitiativeManager {
    private val initiativeComparator = Comparator<InitiativeQueueItem> { left, right ->
        when {
            left.total > right.total -> -1
            left.total < right.total -> 1
            else -> when {
                left.initiative > right.initiative -> -1
                left.initiative < right.initiative -> 1
                else -> when {
                    left.index > right.index -> 1
                    left.index < right.index -> -1
                    else -> 0
                }
            }
        }
    }

    private val sceneInitiativeQueue = PriorityQueue(initiativeComparator)
    private var initiativeQueue = PriorityQueue(initiativeComparator)

    fun printInitiativeQueue(stringBuffer: StringBuffer) {
        stringBuffer.appendln("__***Resumo***__")

        val queue = PriorityQueue(initiativeQueue)

        var index = 1
        while (queue.peek() != null)
        {
            val item = queue.poll()
            stringBuffer.append("$index. <@${item.userId.asString()}>")
            if(item.name != null) {
                stringBuffer.append("(${item.name.trim()})")
            }
            stringBuffer.append(" -> ")
            if (item.penalty != null) {
                stringBuffer.append("Penalidade: ${item.penalty} | ")
            }
            stringBuffer.appendln(item.total.toString())
            index++
        }
    }

    private fun PriorityQueue<InitiativeQueueItem>.addActions(initiativeQueueItem: InitiativeQueueItem, withoutPenalty: Boolean, actions: Int?) {
        this.add(if (!withoutPenalty && actions != null)
            initiativeQueueItem.copy(penalty = -actions)
        else initiativeQueueItem)
        if (actions != null) {
            for (index in 2..actions + 1) {
                this.add(initiativeQueueItem.copy(index = index,
                        penalty = if (withoutPenalty) null else -(actions + index - 1)))
            }
        }
    }

    fun addInitiativeItem(initiativeQueueItem: InitiativeQueueItem, withoutPenalty: Boolean, actions: Int?) {
        sceneInitiativeQueue.addActions(initiativeQueueItem, withoutPenalty, actions)
        initiativeQueue.addActions(initiativeQueueItem, withoutPenalty, actions)
    }

    fun clearInitiativeQueue() {
        sceneInitiativeQueue.clear()
        initiativeQueue.clear()
    }
}