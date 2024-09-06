package com.example.procoreinterview.util


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `test string contains no numbers returns false`() {
        val result = "NoNumber".containsNumber()
        assertThat(result).isFalse()
    }

    @Test
    fun `test string contains number return true`() {
        val result = "Number5".containsNumber()
        assertThat(result).isTrue()
    }

    @Test
    fun `test string contains no upper case returns false`() {
        val result = "smalls".containsUpperCase()
        assertThat(result).isFalse()
    }
    @Test
    fun `test string contains no upper case returns true`() {
        val result = "Upper Case".containsUpperCase()
        assertThat(result).isTrue()
    }


}