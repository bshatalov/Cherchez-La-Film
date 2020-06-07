package com.jefflogic.cherchezlafilm

//import android.support.v7.widget.RecyclerView
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jefflogic.cherchezlafilm.MainActivity.Companion.getColumnsNum
import com.jefflogic.cherchezlafilm.MainActivity.Companion.mItemSelected
import kotlinx.android.synthetic.main.item_film.view.*


private val TAG: String = "MY" //RecyclerItemViewHolder::class.java.simpleName

class FilmItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) /*, View.OnClickListener*/{
    fun bind(item: FilmItem, position: Int) {
        itemView.mItemImageView.setImageResource(item.image)
        itemView.mItemTextView.text = item.textStr // itemView.mItemTextView.context.getString(item.text) + " " + (position - getColumnsNum() + 1)
        //item.view = itemView
        itemView.mItemTextView?.highlight(position == mItemSelected)
        if (item.like)
            itemView.mItemImageViewLike.setImageResource(
                R.drawable.ic_favorite_green_24dp
            )
        else
            itemView.mItemImageViewLike.setImageResource(
                0
                //R.drawable.ic_favorite_border_black_24dp
            )
    }

    // highlight the view
    private fun AppCompatTextView.highlight(
        highlight: Boolean
    ) {
        if (highlight)
            this.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent))
        else
            this.setTextColor(ContextCompat.getColor(this.context, R.color.black))
    }

}