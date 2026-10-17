package com.example.timerapp

import com.example.timerapp.util.Helper
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ParametertizedTest(val Text: String, val expected: Boolean) {

    @Test
    fun isPalindrome() {

        //Arrange
        val helper = Helper()

        //Act
        val result = helper.isPalindrome(Text)

        //Assert
        assertEquals(expected, result)
    }


    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index} -> {0} is pallidrom {1}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf("hello", false),
                arrayOf("level", true),
                arrayOf("a", true),
                arrayOf("", true),
            )
        }
    }
}