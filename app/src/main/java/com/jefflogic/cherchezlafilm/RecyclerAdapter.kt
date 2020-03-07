package com.jefflogic.cherchezlafilm

//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerAdapter(private val mItemList: List<String>?) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return RecyclerItemViewHolder.newInstance(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val holder: RecyclerItemViewHolder = viewHolder as RecyclerItemViewHolder
        val itemText = mItemList!![position]
        holder.setItemText(itemText)
    }

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }

}