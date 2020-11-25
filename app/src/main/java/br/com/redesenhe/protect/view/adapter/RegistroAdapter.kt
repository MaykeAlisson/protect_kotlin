package br.com.redesenhe.protect.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.listener.RegistroListener
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.view.viewholder.RegistroViewHolder

class RegistroAdapter : RecyclerView.Adapter<RegistroViewHolder>() {

    private var mList: List<RegistroModel> = arrayListOf()
    private lateinit var mListener: RegistroListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return RegistroViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: RegistroListener) {
        mListener = listener
    }

    fun updateList(list: List<RegistroModel>) {
        mList = list
        notifyDataSetChanged()
    }
}