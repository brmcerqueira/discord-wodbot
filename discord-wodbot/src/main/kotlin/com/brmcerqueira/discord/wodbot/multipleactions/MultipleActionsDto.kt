package com.brmcerqueira.discord.wodbot.multipleactions

import com.brmcerqueira.discord.wodbot.PenaltyMode

data class MultipleActionsDto(val actions: Int, val penaltyMode: PenaltyMode, val characterId: Int? = null)