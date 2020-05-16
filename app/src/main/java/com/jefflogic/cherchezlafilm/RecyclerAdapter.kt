package com.jefflogic.cherchezlafilm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RecyclerAdapter() : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        if (viewType == TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)
            return RecyclerItemViewHolder.newInstance(view)
        } else if (viewType == TYPE_HEADER) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.item_header, parent, false)
            return RecyclerHeaderViewHolder(view)
        }
        throw RuntimeException("There is no type that matches the type $viewType + make sure your using types correctly")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isPositionHeader(position)) {
            val hholder = holder as RecyclerItemViewHolder
            // we are taking header into account so all of our items are correctly positioned
            hholder.setItem(
                 position
                ,MainActivity.getItem(position)
/*
                ,MainActivity.getItemImageResId(position)
                ,MainActivity.getItemTextResId (position)
                ,MainActivity.getItemNoteResId (position)
*/
            )
        }
    }

    val basicItemCount: Int
        get() = MainActivity.itemCount

    //our new getItemCount() that includes header View
    override fun getItemCount(): Int {
        return basicItemCount + 1 // +header
    }

    // returns viewType for a given position
    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) {
            TYPE_HEADER
        } else TYPE_ITEM
    }

    // check if given position is a header
    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    companion object {
        private const val TYPE_HEADER = 2
        private const val TYPE_ITEM = 1
    }
}
