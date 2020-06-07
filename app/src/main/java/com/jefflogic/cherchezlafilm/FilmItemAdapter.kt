package com.jefflogic.cherchezlafilm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jefflogic.cherchezlafilm.MainActivity.Companion.getColumnsNum
import com.jefflogic.cherchezlafilm.MainActivity.Companion.getItemPos
import kotlinx.android.synthetic.main.item_favourite_film.view.*
import kotlinx.android.synthetic.main.item_film.view.*

private val TAG: String = DetailsActivity::class.java.simpleName


class FilmItemAdapter(val inflater: LayoutInflater, val items: MutableList<FilmItem>
                      , val clickListener: (itemView: View, filmItem: FilmItem, viewHolderPosition: Int/*, itemPosition: Int*/) -> Unit /* OnFilmClickListener*/
                      , val longClickListener: (itemView: View, filmItem: FilmItem, viewHolderPosition: Int/*, itemPosition: Int*/) -> Boolean /* OnFilmClickListener*/

) : RecyclerView.Adapter<ViewHolder>() {

    //new:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //orientation = parent.getResources().getConfiguration().orientation
        //Log.d(TAG, "onCreateViewHolder orientation = $orientation")

        if (parent.id == R.id.mRecyclerView) {
            if (viewType == VIEW_TYPE_ITEM) {
                return FilmItemViewHolder(inflater.inflate(R.layout.item_film, parent, false))
            } else if (viewType == VIEW_TYPE_HEADER) {
                return FilmHeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false))
            }
        } else if (parent.id == R.id.mFavRecyclerView) {
            if (viewType == VIEW_TYPE_ITEM) {
                return FavouriteFilmItemViewHolder(inflater.inflate(R.layout.item_favourite_film, parent, false))
            } else if (viewType == VIEW_TYPE_HEADER) {
                return FilmHeaderViewHolder(inflater.inflate(R.layout.item_favourite_header, parent, false))
            }
        }
        throw RuntimeException("There is no type that matches the type $viewType + make sure your using types correctly")
    }

    //new:
    override fun getItemCount(): Int {
        return basicItemCount + getColumnsNum()
    }

/*
    interface OnFilmClickListener {
        fun onFilmClick(filmItem: FilmItem)
    }
*/

    //new:
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d(TAG, "onBindViewHolder orientation = $orientation position = $position ")
        if (holder is FilmItemViewHolder) {
            val itemPosition = position - getColumnsNum()
            val item = items[itemPosition]
            holder.bind(item, position)
            holder.itemView.mItemDetailsButton.setOnClickListener{ clickListener(holder.itemView, item, position/*, itemPosition*/) }
            holder.itemView.setOnLongClickListener{ longClickListener(holder.itemView, item, position/*, itemPosition*/) }
        } else if (holder is FavouriteFilmItemViewHolder) {
            val itemPosition = position - getColumnsNum()
            val item = items[itemPosition]
            holder.bind(item, position)
            holder.itemView.mItemDeleteBtn.setOnClickListener{ clickListener(holder.itemView, item, position/*, itemPosition*/) }
            holder.itemView.setOnLongClickListener{ longClickListener(holder.itemView, item, position/*, itemPosition*/) }
        }

//        if (!isPositionHeader(position)) {
/*
            val hholder = holder as FilmItemViewHolder
            hholder.bind(items.get(position), position)
*/
            // we are taking header into account so all of our items are correctly positioned
/*
            hholder.setItem(
                 position
                ,MainActivity.getItem(position)
            )
*/
//        }
    }

    val basicItemCount: Int
        get() = items.size

    //our new getItemCount() that includes header View
    //old:

    // returns viewType for a given position
    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) {
            VIEW_TYPE_HEADER
        } else
            VIEW_TYPE_ITEM
    }

    // check if given position is a header
    private fun isPositionHeader(position: Int): Boolean {
        return position < getColumnsNum()
    }

    public fun removeAt(position: Int) {
        items.removeAt(getItemPos(position))
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }
    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
        //var orientation: Int = Configuration.ORIENTATION_UNDEFINED
    }
}
