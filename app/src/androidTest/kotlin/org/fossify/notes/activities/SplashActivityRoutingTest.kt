package org.fossify.notes.activities

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.fossify.notes.helpers.OPEN_NOTE_ID
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityRoutingTest {
    @Before
    fun setUp() {
        Intents.init()
        intending(hasComponent(MainActivity::class.java.name))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun forwardsOpenNoteIdToMainActivity() {
        val noteId = 42L
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val intent = Intent(context, SplashActivity::class.java).apply {
            putExtra(OPEN_NOTE_ID, noteId)
        }

        ActivityScenario.launch<SplashActivity>(intent).use {
            intended(
                allOf(
                    hasComponent(MainActivity::class.java.name),
                    hasExtra(OPEN_NOTE_ID, noteId),
                )
            )
        }
    }
}
