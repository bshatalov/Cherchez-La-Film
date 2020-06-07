package com.jefflogic.cherchezlafilm

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jefflogic.cherchezlafilm.MainActivity.Companion.getItemPos
import kotlinx.android.synthetic.main.activity_favourite.*
import java.util.ArrayList

private val TAG: String = FavouriteActivity::class.java.simpleName

class FavouriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        Log.d(TAG, "onCreate")

        //FavouritesActivity.orientation = getResources().getConfiguration().orientation

        //setSupportActionBar(mToolbar)
        //mToolbar.setTitleTextColor(Color.WHITE)

        initItems()
        initRecycler()
        //initClickListeners()

        // Восстановить состояние
        if (savedInstanceState != null) {
            //FilmItemViewHolder.mItemSelected = savedInstanceState.getInt(SELECTED_POSITION_CODE)
            //MainActivity.mItemSelected = savedInstanceState.getInt(SELECTED_POSITION_CODE)
        }
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }

    fun initItems() {
        Log.d(TAG, "initItems()")
        items.clear()
        for(i in 0 until MainActivity.items.size){
            if (MainActivity.items[i].like) {
                Log.d(TAG, "i=$i")
                val item = MainActivity.items[i]
                val favFilmItem = FilmItem(
                     item.image
                    ,item.text
                    ,item.textStr
                    ,item.note
                    ,i  // parent item
                )
                favFilmItem.like = true
                items.add(favFilmItem)
            }
        }
    }

    fun prnItems() {
        Log.d(TAG, "items list: ")
        for(i in 0 until items.size){
            Log.d(TAG, "i=$i, item=${items[i].textStr} ")
        }
    }

    fun initRecycler() {
        val layoutManager = GridLayoutManager(
            this,
            when(orientation) {Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMNS; Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMNS; else -> PORTRAIT_COLUMNS},  /* число столбцов */
            LinearLayoutManager.VERTICAL,  /* вертикальная ориентация */
            false
        )
        mFavRecyclerView.layoutManager = layoutManager
/*
        mFavRecyclerView.adapter = FilmItemAdapter(LayoutInflater.from(this), mItemList, object: FilmItemAdapter.OnFilmClickListener{
            override fun onFilmClick(filmItem: FilmItem) {
                //startActivity
            }
        })
*/
        mFavRecyclerView.adapter = FilmItemAdapter(
            LayoutInflater.from(this), items,
            //ClickListener: to mItemDetailsButton
            { itemView, filmItem, position /*, itemPosition*/ ->
                itemUnlikeClick(filmItem, position)
                //mFavRecyclerView.adapter?.notifyItemRemoved(position)
                //startActivity -> it: FilmItem
                //Toast.makeText(this, filmItem.note, Toast.LENGTH_SHORT).show()
                //val item = items[realPosition] //items.find {filmItem === it }
                //filmItem.color = Color.RED
/*
                if (mFavRecyclerView.adapter is FilmItemAdapter) {
                    (mFavRecyclerView.adapter as FilmItemAdapter).removeAt(position)
                }
*/
            },
            //LongClickListener:
            { itemView, filmItem, position /*, itemPosition*/ ->
                //Toast.makeText(this, filmItem.note, Toast.LENGTH_SHORT).show()
                //val item = items[realPosition] //items.find {filmItem === it }
                //setImageLike(itemView, filmItem, position)
                itemUnlikeClick(filmItem, position)
/*
                if (mFavRecyclerView.adapter is FilmItemAdapter) {
                    (mFavRecyclerView.adapter as FilmItemAdapter).removeAt(position)
                }
*/
                //Log.d(TAG, "longClickListener: notifyItemChanged at position $position")
                false
            }
        )

/*
        mFavRecyclerView.setOnScrollListener(object : HidingScrollListener() {
            override fun onHide() = hideViews()
            override fun onShow() = showViews()
        })
*/
        /*mFavRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == MainActivity.items.size) {
                    repeat(4) {
                        addRandom()
                    }
                    recyclerView.adapter?.notifyItemRangeInserted(MainActivity.items.size - 3, 4)
                }
            }
        })
        */
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        //itemDecoration.setDrawable(getDrawable(R.drawable.black_line_5dp)!!)
        mFavRecyclerView.addItemDecoration(itemDecoration)

        mFavSwipeRefreshLayout.setOnRefreshListener {  }
    }

    fun itemUnlikeClick(filmItem: FilmItem, position: Int) {
        //val filmItem = getFilmItem(position)
        //Log.d(TAG, "itemUnlikeClick: position = $position, text = ${filmItem.textStr}")
        MainActivity.items[filmItem.parent!!].like = false
        //filmItem.like = false
        if (mFavRecyclerView.adapter is FilmItemAdapter) {
            (mFavRecyclerView.adapter as FilmItemAdapter).removeAt(position)
        }
        //items.removeAt(getItemPos(position))
        //Log.d(TAG, "before delete: ")
        //prnItems()
        //items.removeAt(position)
    }

    companion object {
        //private val items: MutableList<FavouriteFilmItem> = ArrayList()
        private val items: MutableList<FilmItem> = ArrayList()
        var orientation: Int = Configuration.ORIENTATION_UNDEFINED

/*
        fun getFavouriteItem(position: Int): FilmItem {
            Log.d(TAG, "getFavouriteItem: position = $position")
            Log.d(TAG, "getItemPos = ${getItemPos(position)}")
            return items[getItemPos(position)]
        }
*/

/*
        fun getFilmItem(position: Int): FilmItem {
            val parent = getFavouriteItem(position).parent!!
            Log.d(TAG, "getFilmItem: position = $position, parent = $parent")
            return MainActivity.items[parent]
        }
*/
    }
}