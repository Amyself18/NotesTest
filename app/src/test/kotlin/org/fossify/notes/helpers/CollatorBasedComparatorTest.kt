package org.fossify.notes.helpers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CollatorBasedComparatorTest {
    private val comparator = CollatorBasedComparator()

    @Test
    fun `compare sorts numeric chunks by number length before lexical value`() {
        val sorted = listOf("Note 10", "Note 2", "Note 1").sortedWith(comparator)

        assertEquals(listOf("Note 1", "Note 2", "Note 10"), sorted)
    }

    @Test
    fun `compare ignores case at primary collator strength`() {
        assertEquals(0, comparator.compare("note", "NOTE"))
    }

    @Test
    fun `compare returns coerced ordering values`() {
        assertTrue(comparator.compare("a", "b") < 0)
        assertTrue(comparator.compare("b", "a") > 0)
    }
}
