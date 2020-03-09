package com.jefflogic.cherchezlafilm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RecyclerAdapter(
     itemImageList: List<Int>?
    ,itemTextList: List<String>?
    ,itemNoteList: List<String>?
) : RecyclerView.Adapter<ViewHolder>() {
    private val mItemImageList:  List<Int>?
    private val mItemTextList: List<String>?
    private val mItemNoteList: List<String>?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        if (viewType == TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
            return RecyclerItemViewHolder.newInstance(view)
        } else if (viewType == TYPE_HEADER) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false)
            return RecyclerHeaderViewHolder(view)
        }
        throw RuntimeException("There is no type that matches the type $viewType + make sure your using types    correctly")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isPositionHeader(position)) {
            val hholder = holder as RecyclerItemViewHolder?
            val itemImage: Int    = mItemImageList!![position - 1]// we are taking header into account so all of our items are correctly positioned
            val itemText : String = mItemTextList !![position - 1]
            val itemNote : String = mItemNoteList !![position - 1]
            hholder!!.setItem(itemImage, itemText, itemNote)
        }
    }

    val basicItemCount: Int
        get() = mItemTextList?.size ?: 0

    //our new getItemCount() that includes header View
    override fun getItemCount(): Int {
        return basicItemCount + 1 // header
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

    init {
        mItemImageList = itemImageList
        mItemTextList  = itemTextList
        mItemNoteList  = itemNoteList
    }
}

/*/

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
/* */