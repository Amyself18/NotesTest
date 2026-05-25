package org.fossify.notes.extensions

import org.fossify.notes.models.Task
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class StringChecklistTest {
    @Test
    fun `parseChecklistItems returns tasks for serialized checklist`() {
        val checklist = """[{"id":1,"dateCreated":10,"title":"Buy milk","isDone":false}]"""

        val tasks = checklist.parseChecklistItems()

        assertEquals(listOf(Task(1, 10, "Buy milk", false)), tasks)
    }

    @Test
    fun `parseChecklistItems returns null for plain text`() {
        assertNull("plain note".parseChecklistItems())
    }

    @Test
    fun `checklistToPlainText keeps unfinished tasks before done tasks by default`() {
        val checklist = """[{"id":1,"dateCreated":10,"title":"Done item","isDone":true},{"id":2,"dateCreated":20,"title":"Open item","isDone":false}]"""

        val plainText = checklist.checklistToPlainText()

        assertEquals("[ ] Open item\n[x] Done item", plainText)
    }

    @Test
    fun `checklistToPlainText keeps original order when requested`() {
        val checklist = """[{"id":1,"dateCreated":10,"title":"Done item","isDone":true},{"id":2,"dateCreated":20,"title":"Open item","isDone":false}]"""

        val plainText = checklist.checklistToPlainText(moveDoneToBottom = false)

        assertEquals("[x] Done item\n[ ] Open item", plainText)
    }
}
