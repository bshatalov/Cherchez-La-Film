package com.jefflogic.cherchezlafilm

//import android.support.v7.widget.RecyclerView
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

private val TAG: String = "MY" //RecyclerItemViewHolder::class.java.simpleName

val POSITION_CODE = "POSITION"
val REQUEST_CODE_LIKE = 1

class RecyclerItemViewHolder(
     parent: View
    ,val mItemImageView     : AppCompatImageView
    ,val mItemTextView      : AppCompatTextView
) : ViewHolder(parent) , View.OnClickListener{
    fun setItem(position: Int, item: Item ) {
        //Log.d(TAG, "position = $position , mItemSelected = $mItemSelected, itemCount = ${MainActivity.itemCount}")
        if (position == mItemSelected) {
            this.itemSelect(position)
        }

        mItemImageView.setImageResource(item.image)
        mItemTextView.text = mItemTextView.context.getString(item.text)
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            when (v.id) {
                R.id.mItemDetailsButton -> itemDetailsClick(v, position)
            }
        }
    }

    private fun AppCompatTextView.highlight(
        par: Boolean
    ) {
        if (par)
            this.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent))
        else
            this.setTextColor(ContextCompat.getColor(this.context, R.color.black))
    }

    private fun itemSelect(position: Int) {
        // previous item -> unHighlight
        mItemTextViewSelected?.highlight(false)

        // save new selected item
        mItemTextViewSelected = this.mItemTextView
        mItemSelected         = position

        // new textView -> highlight
        mItemTextViewSelected?.highlight(true)
    }

    private fun itemDetailsClick(v: View, position: Int) {
        //Log.d(TAG, "itemClick() at position $position")

        // new item is selected
        itemSelect(position)

        val intent = Intent(v.context as Activity, DetailsActivity::class.java)
        intent.putExtra(POSITION_CODE, position)
        startActivityForResult(v.context as Activity, intent, REQUEST_CODE_LIKE, null)
    }

    companion object {
        var mItemSelected         : Int? = null                 //choosen position
        var mItemTextViewSelected : AppCompatTextView? = null   //textView for choosen position

        fun newInstance(parent: View): RecyclerItemViewHolder {
            val itemTextView  = parent.findViewById<View>(R.id.mItemTextView ) as AppCompatTextView
            val itemImageView = parent.findViewById<View>(R.id.mItemImageView) as AppCompatImageView

            val x = RecyclerItemViewHolder(parent, itemImageView, itemTextView)
            val itemDetailsButton = parent.findViewById<View>(R.id.mItemDetailsButton) as AppCompatButton
            itemDetailsButton.setOnClickListener(x)

            return x
        }
    }
}