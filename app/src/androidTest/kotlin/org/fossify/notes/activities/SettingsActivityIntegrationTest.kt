package org.fossify.notes.activities

import android.content.Context
import android.widget.CompoundButton
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.fossify.notes.R
import org.fossify.notes.extensions.config
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityIntegrationTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.config.enableLineWrap = true
    }

    @After
    fun tearDown() {
        context.config.enableLineWrap = true
    }

    @Test
    fun tappingLineWrapSettingTogglesSwitchAndPersistedConfig() {
        ActivityScenario.launch(SettingsActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertTrue(activity.lineWrapSwitch().isChecked)
            }

            onView(withId(R.id.settings_enable_line_wrap_holder))
                .perform(scrollTo(), click())

            scenario.onActivity { activity ->
                assertFalse(activity.lineWrapSwitch().isChecked)
                assertFalse(activity.config.enableLineWrap)
            }

            onView(withId(R.id.settings_enable_line_wrap_holder))
                .perform(scrollTo(), click())

            scenario.onActivity { activity ->
                assertTrue(activity.lineWrapSwitch().isChecked)
                assertTrue(activity.config.enableLineWrap)
            }
        }
    }

    private fun SettingsActivity.lineWrapSwitch(): CompoundButton {
        return findViewById(R.id.settings_enable_line_wrap)
    }
}
