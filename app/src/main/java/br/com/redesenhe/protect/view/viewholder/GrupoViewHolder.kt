package br.com.redesenhe.protect.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.listener.GrupoListener
import br.com.redesenhe.protect.service.model.GrupoModel

class GrupoViewHolder(itemView: View, private val listener: GrupoListener) :
    RecyclerView.ViewHolder(itemView) {

    private val mGrupo_row_item : LinearLayout = itemView.findViewById(R.id.grupo_row_item)
    private val mImageViewMundo : ImageView = itemView.findViewById(R.id.grupo_item_icone_mundo)
    private val mTextNome : TextView = itemView.findViewById(R.id.grupo_item_nome)
    private val mImageViewPaste : ImageView = itemView.findViewById(R.id.grupo_item_icone_key)


    /**
     * Atribui valores aos elementos de interface e também eventos
     */
    fun bindData(grupo: GrupoModel) {

        this.mImageViewMundo.setImageResource(R.drawable.icone_mundo)
        this.mTextNome.text = grupo.nome
        this.mImageViewPaste.setImageResource(R.drawable.icone_pasta)

        mGrupo_row_item.setOnClickListener{  listener.onListClick(grupo.id) }


//        listener.onListClick(grupo.id.toL)

    }
}