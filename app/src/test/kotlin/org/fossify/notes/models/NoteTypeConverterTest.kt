package org.fossify.notes.models

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteTypeConverterTest {
    private val converter = NoteTypeConverter()

    @Test
    fun `fromValue returns text type for unknown values`() {
        assertEquals(NoteType.TYPE_TEXT, NoteType.fromValue(-1))
        assertEquals(NoteType.TYPE_TEXT, NoteType.fromValue(99))
    }

    @Test
    fun `converter maps note types to stored integer values`() {
        assertEquals(0, converter.fromNoteType(NoteType.TYPE_TEXT))
        assertEquals(1, converter.fromNoteType(NoteType.TYPE_CHECKLIST))
    }

    @Test
    fun `converter restores note types from stored integer values`() {
        assertEquals(NoteType.TYPE_TEXT, converter.toNoteType(0))
        assertEquals(NoteType.TYPE_CHECKLIST, converter.toNoteType(1))
    }
}
