package com.jefflogic.cherchezlafilm

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jefflogic.cherchezlafilm.MainActivity.Companion.mItemSelected
import kotlinx.android.synthetic.main.item_film.view.*


private val TAG: String = FavouriteFilmItemViewHolder::class.java.simpleName

class FavouriteFilmItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView){

    fun bind(item: FilmItem, position: Int) {
        itemView.mItemImageView.setImageResource(item.image)
        itemView.mItemTextView.text = item.textStr
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
}