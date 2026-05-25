package org.fossify.notes.models

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.fossify.commons.helpers.PROTECTION_NONE
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File

class NoteMockKTest {
    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getNoteStoredValue returns inline note value when path is empty`() {
        val note = note(value = "Inline note", path = "")
        val context = mockk<Context>(relaxed = true)

        val storedValue = note.getNoteStoredValue(context)

        assertEquals("Inline note", storedValue)
    }

    @Test
    fun `getNoteStoredValue reads note content from filesystem path`() {
        val file = File.createTempFile("note", ".txt").apply {
            writeText("File-backed note")
            deleteOnExit()
        }
        val note = note(path = file.absolutePath)
        val context = mockk<Context>(relaxed = true)

        val storedValue = note.getNoteStoredValue(context)

        assertEquals("File-backed note", storedValue)
    }

    @Test
    fun `getNoteStoredValue reads note content from content resolver`() {
        val path = "content://org.fossify.notes.test/note/1"
        val uri = mockk<Uri>()
        val resolver = mockk<ContentResolver>()
        val context = mockk<Context>()
        mockkStatic(Uri::class)
        every { Uri.parse(path) } returns uri
        every { context.contentResolver } returns resolver
        every { resolver.openInputStream(uri) } returns ByteArrayInputStream("Resolver-backed note".toByteArray())
        val note = note(path = path)

        val storedValue = note.getNoteStoredValue(context)

        assertEquals("Resolver-backed note", storedValue)
    }

    @Test
    fun `getNoteStoredValue returns null when external content cannot be read`() {
        val path = "content://org.fossify.notes.test/missing"
        val uri = mockk<Uri>()
        val resolver = mockk<ContentResolver>()
        val context = mockk<Context>()
        mockkStatic(Uri::class)
        every { Uri.parse(path) } returns uri
        every { context.contentResolver } returns resolver
        every { resolver.openInputStream(uri) } throws IllegalStateException("missing")
        val note = note(path = path)

        val storedValue = note.getNoteStoredValue(context)

        assertNull(storedValue)
    }

    private fun note(
        value: String = "",
        path: String = ""
    ) = Note(
        id = null,
        title = "Test note",
        value = value,
        type = NoteType.TYPE_TEXT,
        path = path,
        protectionType = PROTECTION_NONE,
        protectionHash = ""
    )
}
