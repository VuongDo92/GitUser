package com.tymex.gituser.presentation.utils

object Utils {

    fun getRoundedNumber(input: Long): String {
        return when {
            input < 10 -> input.toString()  // Numbers below 10 are returned as is
            input < 100 -> {
                if (input == 90L) "90"  // Special case for 90
                else "${input / 10 * 10}+"  // Round down to the nearest 10 and add +
            }
            input < 1000 -> {
                if (input % 100 == 0L) input.toString()  // Exact hundreds are returned as is
                else "${input / 100 * 100}+"  // Round down to the nearest 100 and add +
            }
            else -> {
                if (input % 1000 == 0L) input.toString()  // Exact multiples of 1000 are returned as is
                else "${input / 1000 * 1000}+"  // Round down to the nearest 1000 and add +
            }
        }
    }
}