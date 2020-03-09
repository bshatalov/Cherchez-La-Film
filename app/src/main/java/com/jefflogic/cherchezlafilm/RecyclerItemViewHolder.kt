package com.jefflogic.cherchezlafilm

//import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RecyclerItemViewHolder(
     parent: View?
    ,private val mItemImageView: androidx.appcompat.widget.AppCompatImageView
    ,private val mItemTextView : TextView
) :
    ViewHolder(parent!!) {
    fun setItem(imageResID: Int, text: CharSequence?, note: CharSequence?) {
        mItemTextView.text = text
        mItemImageView.setImageResource(imageResID)
        mItemImageView.contentDescription = note
    }

    companion object {
        fun newInstance(parent: View): RecyclerItemViewHolder {
            val itemTextView  = parent.findViewById<View>(R.id.itemTextView ) as TextView
            val itemImageView = parent.findViewById<View>(R.id.itemImageView) as androidx.appcompat.widget.AppCompatImageView

            return RecyclerItemViewHolder(parent, itemImageView, itemTextView)
        }
    }

}