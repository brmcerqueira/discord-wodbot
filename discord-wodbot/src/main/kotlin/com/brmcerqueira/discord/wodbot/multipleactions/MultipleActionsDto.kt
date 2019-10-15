package com.brmcerqueira.discord.wodbot.multipleactions

data class MultipleActionsDto(val actions: Int, val withoutPenalty: Boolean, val characterId: Int? = null)