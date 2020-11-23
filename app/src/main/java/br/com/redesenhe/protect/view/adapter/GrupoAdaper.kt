package br.com.redesenhe.protect.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.listener.GrupoListener
import br.com.redesenhe.protect.service.model.GrupoModel
import br.com.redesenhe.protect.view.viewholder.GrupoViewHolder

class GrupoAdaper : RecyclerView.Adapter<GrupoViewHolder>(){

    private var mList: List<GrupoModel> = arrayListOf()
    private lateinit var mListener: GrupoListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return GrupoViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: GrupoListener) {
        mListener = listener
    }

    fun updateList(list: List<GrupoModel>) {
        mList = list
        notifyDataSetChanged()
    }

}