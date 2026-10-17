package com.example.timerapp

import com.example.timerapp.util.Helper
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class HelperTest {
    @Before
    fun before() {
        println(" before run this ")
    }

    @After
    fun after() {
        println(" after run this ")
    }

    @Test
    fun isPalindrome() {

        //Arrange
        val helper = Helper()
        //Act
        val result = helper.isPalindrome("hello")
        //Assert
        assertEquals(false, result)

    }

    @Test
    fun islevelPalidrome() {

        val helper = Helper()
        val result = helper.isPalindrome("level")
        assertEquals(true, result)

    }
}