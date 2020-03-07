package com.jefflogic.cherchezlafilm

//import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RecyclerItemViewHolder(parent: View?, private val mItemTextView: TextView) :
    ViewHolder(parent!!) {
    fun setItemText(text: CharSequence?) {
        mItemTextView.text = text
    }

    companion object {
        fun newInstance(parent: View): RecyclerItemViewHolder {
            val itemTextView =
                parent.findViewById<View>(R.id.itemTextView) as TextView
            return RecyclerItemViewHolder(parent, itemTextView)
        }
    }

}