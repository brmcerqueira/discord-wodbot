package com.brmcerqueira.discord.wodbot

enum class PenaltyMode {
    None,
    Defensive,
    Offensive;

    companion object {
        fun parse(matchResult: MatchResult): PenaltyMode = when {
            matchResult.groups["penaltyModeNone"] != null -> None
            matchResult.groups["penaltyModeDefensive"] != null -> Defensive
            else -> Offensive
        }
    }

    override fun toString(): String = when(this) {
        Defensive -> "Defensiva"
        Offensive -> "Ofensiva"
        else -> "Nenhuma"
    }
}