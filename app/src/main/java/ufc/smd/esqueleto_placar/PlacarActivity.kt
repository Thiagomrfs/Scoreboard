package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.google.android.material.snackbar.Snackbar
import data.Placar
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets
import java.util.*


class PlacarActivity : AppCompatActivity() {
    // coisas do timer -----------------------------------
    var timer: CountDownTimer? = null
    var timerView: TextView? = null
    var timerToggle: Button? = null

    var timerRodando = false

    var tempoRestanteEmMilis: Long = 600000

    // cabou -------------------------------------------

    lateinit var placar:Placar

    var timeEsquerda : Int = 0
    var timeDireita : Int = 0

    var currentQuarterIndex: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)

        placar = getIntent().getExtras()?.getSerializable("placar") as Placar

        timerView = findViewById<TextView>(R.id.timer)
        currentQuarterIndex = findViewById<TextView>(R.id.quarterIndex)
        timerToggle = findViewById<Button>(R.id.toggle)

        timerToggle?.setOnClickListener {
            if (timerRodando) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        initInterface()
    }

    fun initInterface(){
        //Configura Interface
        val tvNomePartida = findViewById<TextView>(R.id.gameName)
        tvNomePartida.text = placar.nome_partida

        val timeA = findViewById<TextView>(R.id.timeA2)
        timeA.text = placar.timeA

        val timeB = findViewById<TextView>(R.id.timeB2)
        timeB.text = placar.timeB

        if (!placar.has_timer) {
            timerView?.text = ""
            timerToggle?.setVisibility(View.INVISIBLE);
        } else {
            updateCountDownText()
        }

        currentQuarterIndex?.text = "T" + placar.quarto_atual
    }


    fun alteraPlacarEsquerda(v:View){
        timeEsquerda += 1

        Log.v("PLACAR", "Time esquerda: ${timeEsquerda}")

        //Atualiza placar
        atualizaPlacar(v)
    }

    fun alteraPlacarDireita(v:View){
        timeDireita += 1

        Log.v("PLACAR", "Time direita: ${timeDireita}")

        atualizaPlacar(v)
    }

    fun voltarEsquerda(v:View){
        if(timeEsquerda >= 1){
            timeEsquerda -= 1
            atualizaPlacar(v)
        }

    }

    fun voltarDireita(v:View){
        if(timeDireita >= 1){
            timeDireita -= 1
            atualizaPlacar(v)
        }

    }

    fun atualizaPlacar(v:View){
        val teamLeft = findViewById<TextView>(R.id.teamLeft)
        teamLeft.setText(timeEsquerda.toString())

        val teamRight = findViewById<TextView>(R.id.teamRight)
        teamRight.setText(timeDireita.toString())

        placar.resultado = ""+timeEsquerda+" vs "+ timeDireita
        vibrar()
    }


    fun vibrar (){
        val buzzer = this.getSystemService<Vibrator>()
         val pattern = longArrayOf(0, 200, 100, 300)
         buzzer?.let {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
             } else {
                 //deprecated in API 26
                 buzzer.vibrate(pattern, -1)
             }
         }

    }


    fun saveGame(v: View) {

        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        val edShared = sp.edit()
        //Salvar o número de jogos já armazenados
        val numMatches= sp.getInt("numberMatch",0) + 1
        edShared.putInt("numberMatch", numMatches)

        //Escrita em Bytes de Um objeto Serializável
        val dt= ByteArrayOutputStream()
        val oos = ObjectOutputStream(dt);
        oos.writeObject(placar);

        //Salvar como "match"
        edShared.putString("match"+numMatches, dt.toString(StandardCharsets.ISO_8859_1.name()))
        edShared.commit() //Não esqueçam de comitar!!!

        //Retorna para home
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun startTimer() {
        timer = object : CountDownTimer(tempoRestanteEmMilis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempoRestanteEmMilis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                timerRodando = false
                timerToggle?.text ="Rodar tempo"
                tempoRestanteEmMilis = 600000

                updateCountDownText()

                vibrar()

                if (placar.quarto_atual < 4) {
                    placar.quarto_atual += 1
                    currentQuarterIndex?.text = "T" + placar.quarto_atual

                    val duration= Snackbar.LENGTH_SHORT
                    val text = "Timer Finalizado! Iniciando novo quarto."

                    val snack= Snackbar.make(timerView as View,text,duration)
                    snack.show()
                } else {
                    val duration= Snackbar.LENGTH_SHORT
                    val text = "Jogo finalizado!"

                    val snack= Snackbar.make(timerView as View,text,duration)
                    snack.show()
                }
            }
        }.start()

        timerRodando = true
        timerToggle?.text = "Parar tempo"
    }

    private fun pauseTimer() {
        timer?.cancel()
        timerRodando = false
        timerToggle?.text = "Rodar tempo"
    }

    private fun updateCountDownText() {
        val minutes = (tempoRestanteEmMilis / 1000).toInt() / 60
        val seconds = (tempoRestanteEmMilis / 1000).toInt() % 60
        val timeLeftFormatted: String =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        timerView?.setText(timeLeftFormatted)
    }

}