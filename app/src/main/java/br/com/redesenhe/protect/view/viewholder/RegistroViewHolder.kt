package br.com.redesenhe.protect.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.listener.RegistroListener
import br.com.redesenhe.protect.service.model.RegistroModel

class RegistroViewHolder(itemView: View, private val listener: RegistroListener) : RecyclerView.ViewHolder(itemView) {

    private val mRegistro_row_item : LinearLayout = itemView.findViewById(R.id.row_item)
    private val mImageViewMundo : ImageView = itemView.findViewById(R.id.row_item_icone_mundo)
    private val mTextNome : TextView = itemView.findViewById(R.id.row_item_nome)
    private val mImageViewKey : ImageView = itemView.findViewById(R.id.row_item_icone_key)

    /**
     * Atribui valores aos elementos de interface e também eventos
     */
    fun bindData(registro: RegistroModel) {

        this.mImageViewMundo.setImageResource(R.drawable.icone_mundo)
        this.mTextNome.text = registro.nome
        this.mImageViewKey.setImageResource(R.drawable.icone_key)

        mRegistro_row_item.setOnClickListener{  listener.onListClick(registro.id, registro.nome) }

    }

}