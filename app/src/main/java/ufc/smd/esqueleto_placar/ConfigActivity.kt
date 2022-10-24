package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import data.Placar

class ConfigActivity : AppCompatActivity() {
    var placar: Placar = Placar(
        "Jogo padrão",
        "Prog Web",
        "Prog Mob",
        "0x0",
        "20/05/20 10h",
        false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
       // placar= getIntent().getExtras()?.getSerializable("placar") as Placar
        //Log.v("PDM22",placar.nome_partida)
        //Log.v("PDM22",placar.has_timer.toString())


        openConfig()
        initInterface()

    }

    fun openConfig()
    {
        val sharedFilename = "configPlacar"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)

        placar.nome_partida=sp.getString("matchname","Jogo Padrão").toString()

        placar.timeA = sp.getString("timeA", "Prog Web").toString()
        placar.timeB = sp.getString("timeB", "Prog Mob").toString()

        placar.has_timer=sp.getBoolean("has_timer",false)

    }

    fun initInterface(){
        val tv= findViewById<EditText>(R.id.gameName)
        tv.setText(placar.nome_partida)

        val timeA = findViewById<EditText>(R.id.timeA)
        timeA.setText(placar.timeA)

        val timeB = findViewById<EditText>(R.id.timeB)
        timeB.setText(placar.timeB)

        val sw= findViewById<Switch>(R.id.swTimer)
        sw.isChecked=placar.has_timer
    }

    fun openPlacar(v: View){ //Executa ao click do Iniciar Jogo
        updatePlacarConfig() //Pega da Interface e joga no placar
        saveConfig() //Salva no Shared preferences
        val intent = Intent(this, PlacarActivity::class.java).apply{
            putExtra("placar", placar)
        }
        startActivity(intent)
    }

    fun updatePlacarConfig(){
        val tv= findViewById<EditText>(R.id.gameName)
        val sw= findViewById<Switch>(R.id.swTimer)

        val timeA = findViewById<EditText>(R.id.timeA)
        val timeB = findViewById<EditText>(R.id.timeB)

        placar.nome_partida= tv.text.toString()

        placar.timeA = timeA.text.toString()
        placar.timeB = timeB.text.toString()

        placar.has_timer=sw.isChecked
    }

    fun saveConfig(){
        val sharedFilename = "configPlacar"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        var edShared = sp.edit()

        edShared.putString("matchname",placar.nome_partida)
        edShared.putBoolean("has_timer",placar.has_timer)

        edShared.putString("timeA", placar.timeA)
        edShared.putString("timeB", placar.timeB)

        edShared.commit()
    }






}