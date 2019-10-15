package com.brmcerqueira.discord.wodbot.dicepool

import discord4j.core.`object`.util.Snowflake

data class DicePoolDto(val amount: Int, val difficulty: Int, val isCanceller: Boolean, val isSpecialization: Boolean)