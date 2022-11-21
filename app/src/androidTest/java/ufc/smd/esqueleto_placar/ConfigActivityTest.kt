package ufc.smd.esqueleto_placar

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import data.Placar
import org.junit.Rule
import org.junit.Test


class ConfigActivityTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(ConfigActivity::class.java)

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
    fun is_setting_gameName() {
        val nameView = onView(withId(R.id.gameName))

        nameView.perform(replaceText(placar.nome_partida))

        nameView.check(matches(withText(placar.nome_partida)))
    }

    @Test
    fun is_setting_teamA() {
        val nameView = onView(withId(R.id.timeA))

        nameView.perform(replaceText(placar.timeA))

        nameView.check(matches(withText(placar.timeA)))
    }

    @Test
    fun is_setting_teamB() {
        val nameView = onView(withId(R.id.timeB))

        nameView.perform(replaceText(placar.timeB))

        nameView.check(matches(withText(placar.timeB)))
    }

}