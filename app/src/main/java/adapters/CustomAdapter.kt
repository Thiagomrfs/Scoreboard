package adapters

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import data.Placar
import ufc.smd.esqueleto_placar.R

class CustomAdapter(private val mList: List<Placar>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    // Criação de Novos ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // infla o card_previous_games
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_previous_game, parent, false)

        return ViewHolder(view)
    }

    // Ligando o Recycler view a um View Holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = ItemView.findViewById(R.id.imageview)
        val tvNomePartida: TextView = ItemView.findViewById(R.id.tvNomePartida)
        val tvResultadoJogo: TextView = ItemView.findViewById(R.id.tvResultadoJogo)
        val lnCell: LinearLayout = ItemView.findViewById(R.id.lnCell)
    }

    // faz o bind de uma ViewHolder a um Objeto da Lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val placarAnterior = mList[position]

        //alimentando os elementos a partir do objeto placar
        holder.tvNomePartida.text = placarAnterior.nome_partida
        holder.tvResultadoJogo.text = placarAnterior.resultado

        holder.lnCell.setOnClickListener{
            val duration= Snackbar.LENGTH_LONG
            val text = placarAnterior.timeA + " vs " +
                    placarAnterior.timeB + " às " +
                    placarAnterior.resultadoLongo

            val snack= Snackbar.make(holder.lnCell,text,duration)
            snack.show()

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


}
