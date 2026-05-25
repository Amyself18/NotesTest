package org.fossify.notes.extensions

import android.content.Context
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import org.fossify.commons.extensions.removeBit
import org.fossify.notes.R
import org.fossify.notes.helpers.FONT_SIZE_150_PERCENT
import org.fossify.notes.helpers.GRAVITY_CENTER
import org.fossify.notes.helpers.GRAVITY_END
import org.fossify.notes.helpers.GRAVITY_START
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class RobolectricAndroidExtensionsTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.config.useIncognitoMode = false
        context.config.fontSizePercentage = 100
        context.config.gravity = GRAVITY_START
    }

    @Test
    fun `application context can read app resources`() {
        assertEquals("Notes_debug", context.getString(R.string.app_launcher_name))
        assertEquals("General note", context.getString(R.string.general_note))
    }

    @Test
    fun `getPercentageFontSize scales shared Android dimension with config value`() {
        context.config.fontSizePercentage = FONT_SIZE_150_PERCENT
        val baseSize = context.resources.getDimension(org.fossify.commons.R.dimen.middle_text_size)

        assertEquals(baseSize * 1.5f, context.getPercentageFontSize(), 0.001f)
    }

    @Test
    fun `getTextGravity maps persisted setting to Android gravity constants`() {
        context.config.gravity = GRAVITY_START
        assertEquals(Gravity.START, context.config.getTextGravity())

        context.config.gravity = GRAVITY_CENTER
        assertEquals(Gravity.CENTER_HORIZONTAL, context.config.getTextGravity())

        context.config.gravity = GRAVITY_END
        assertEquals(Gravity.END, context.config.getTextGravity())
    }

    @Test
    fun `maybeRequestIncognito enables no personalized learning flag`() {
        context.config.useIncognitoMode = true
        val textView = TextView(context)

        textView.maybeRequestIncognito()

        assertTrue(textView.imeOptions and EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING != 0)
    }

    @Test
    fun `maybeRequestIncognito removes no personalized learning flag`() {
        context.config.useIncognitoMode = false
        val textView = TextView(context).apply {
            imeOptions = EditorInfo.IME_ACTION_DONE or EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING
        }

        textView.maybeRequestIncognito()

        assertEquals(EditorInfo.IME_ACTION_DONE, textView.imeOptions)
        assertEquals(
            EditorInfo.IME_ACTION_DONE,
            (EditorInfo.IME_ACTION_DONE or EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING)
                .removeBit(EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING)
        )
    }
}
