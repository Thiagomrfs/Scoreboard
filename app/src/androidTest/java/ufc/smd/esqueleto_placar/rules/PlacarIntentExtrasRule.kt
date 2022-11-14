package ufc.smd.esqueleto_placar.rules

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import data.Placar
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import ufc.smd.esqueleto_placar.PlacarActivity

class PlacarIntentExtrasRule : TestWatcher() {
    override fun starting(description: Description) {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext

        val placar = Placar(
            "Jogo padrão",
            "Prog Web",
            "Prog Mob",
            "0x0",
            "20/05/20 10h",
            true,
            1
        )

        val result = Intent(targetContext, PlacarActivity::class.java).apply {
            putExtra("placar", placar)
        }

        ActivityScenario.launch<PlacarActivity>(result)

        super.starting(description)
    }

    override fun finished(description: Description) {
        super.starting(description)
    }
}