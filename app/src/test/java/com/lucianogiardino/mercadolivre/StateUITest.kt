package com.lucianogiardino.mercadolivre

import com.lucianogiardino.mercadolivre.presentation.StateUI
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlin.test.Test

class StateUITest {

    @Test
    fun `StateUI Loading is correct`() {
        val state: StateUI<Nothing> = StateUI.Loading
        assertTrue(state is StateUI.Loading)
    }

    @Test
    fun `StateUI Waiting is correct`() {
        val state: StateUI<Nothing> = StateUI.Waiting
        assertTrue(state is StateUI.Waiting)
    }

    @Test
    fun `StateUI Success contains correct data`() {
        val data = "Test data"
        val state: StateUI<String> = StateUI.Success(data)

        assertTrue(state is StateUI.Success)
        assertEquals(data, (state as StateUI.Success).data)
    }

    @Test
    fun `StateUI Error contains correct exception`() {
        val exception = RuntimeException("Test exception")
        val state: StateUI<Nothing> = StateUI.Error(exception)

        assertTrue(state is StateUI.Error)
        assertEquals(exception, (state as StateUI.Error).exception)
    }
}