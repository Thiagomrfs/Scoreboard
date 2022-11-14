package ufc.smd.esqueleto_placar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import data.Placar
import org.junit.Rule
import org.junit.Test
import ufc.smd.esqueleto_placar.rules.PlacarIntentExtrasRule


class PlacarActivityTest {
    @get:Rule
    val intentRule = PlacarIntentExtrasRule()

    val placar = Placar(
        "Jogo padrão",
        "Prog Web",
        "Prog Mob",
        "0x0",
        "20/05/20 10h",
        true,
        1
    )

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

    @Test
    fun timer_isVisible() {
        onView(withId(R.id.timer)).check(matches(isDisplayed()))
    }

    @Test
    fun quarter_isVisible() {
        onView(withId(R.id.quarterIndex)).check(matches(isDisplayed()))
    }

    @Test
    fun matchName_isCorrect() {
        onView(withId(R.id.gameName)).check(matches(withText(placar.nome_partida)))
    }

    @Test
    fun leftTeam_isCorrect() {
        onView(withId(R.id.timeA2)).check(matches(withText(placar.timeA)))
    }

    @Test
    fun rightTeam_isCorrect() {
        onView(withId(R.id.timeB2)).check(matches(withText(placar.timeB)))
    }
}