package com.tymex.gituser.presentation.utils

import com.tymex.gituser.presentation.utils.Utils.getRoundedNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtilsTest {


    @Test
    fun `test input less than 10 returns as is`() {
        assertEquals("5", getRoundedNumber(5))
        assertEquals("9", getRoundedNumber(9))
    }

    @Test
    fun `test input less than 100 rounds down to nearest 10`() {
        assertEquals("20+", getRoundedNumber(27))
        assertEquals("90", getRoundedNumber(90))  // Special case for 90
        assertEquals("80+", getRoundedNumber(85))
    }

    @Test
    fun `test input less than 1000 rounds down to nearest 100`() {
        assertEquals("200+", getRoundedNumber(256))
        assertEquals("400+", getRoundedNumber(499))
        assertEquals("300", getRoundedNumber(300))  // Exact hundreds
    }

    @Test
    fun `test input greater than or equal to 1000 rounds down to nearest 1000`() {
        assertEquals("1000", getRoundedNumber(1000))  // Exact multiples of 1000
        assertEquals("2000+", getRoundedNumber(2435))
        assertEquals("5000+", getRoundedNumber(5999))
    }
}