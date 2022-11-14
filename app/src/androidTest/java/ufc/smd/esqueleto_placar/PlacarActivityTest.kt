package ufc.smd.esqueleto_placar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import data.Placar
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ufc.smd.esqueleto_placar.rules.PlacarIntentExtrasRule


class PlacarActivityTest {
    @get:Rule
    val intentRule = PlacarIntentExtrasRule()

    @Test
    fun leftTeam_isAddingPoint() {
        val teamView = onView(withId(R.id.teamLeft))

        teamView.perform(click())

        teamView.check(matches(withText("1")))
    }

    @Test
    fun rightTeam_isAddingPoint() {
        val teamView = onView(withId(R.id.teamLeft))

        teamView.perform(click())

        teamView.check(matches(withText("0")))
    }

    @Test
    fun leftTeam_isRemovingPoint() {
        val teamView = onView(withId(R.id.teamLeft))

        teamView.perform(click())

        onView(withId(R.id.voltarLeft)).perform(click())

        teamView.check(matches(withText("0")))
    }

    @Test
    fun rightTeam_isRemovingPoint() {
        val teamView = onView(withId(R.id.teamRight))

        teamView.perform(click())

        onView(withId(R.id.voltarRight)).perform(click())

        teamView.check(matches(withText("0")))
    }
}