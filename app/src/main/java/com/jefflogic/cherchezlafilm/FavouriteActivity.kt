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

        initItems()
        initRecycler()
    }


    fun initItems() {
        // fill only favourite items from MainActivity.items
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

    fun initRecycler() {
        val layoutManager = GridLayoutManager(
            this,
            when(orientation) {Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMNS; Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMNS; else -> PORTRAIT_COLUMNS},  /* число столбцов */
            LinearLayoutManager.VERTICAL,  /* вертикальная ориентация */
            false
        )
        mFavRecyclerView.layoutManager = layoutManager
        mFavRecyclerView.adapter = FilmItemAdapter(
            LayoutInflater.from(this), items,
            //ClickListener:
            { itemView, filmItem, position ->
                itemUnlikeClick(filmItem, position)
            },
            //LongClickListener:
            { itemView, filmItem, position ->
                itemUnlikeClick(filmItem, position)
                false
            }
        )

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mFavRecyclerView.addItemDecoration(itemDecoration)

        mFavSwipeRefreshLayout.setOnRefreshListener {  }
    }

    // Unlike and remove from favourites screen
    fun itemUnlikeClick(filmItem: FilmItem, position: Int) {
        MainActivity.items[filmItem.parent!!].like = false
        if (mFavRecyclerView.adapter is FilmItemAdapter) {
            (mFavRecyclerView.adapter as FilmItemAdapter).removeAt(position)
        }
    }

    companion object {
        private val items: MutableList<FilmItem> = ArrayList()
        var orientation: Int = Configuration.ORIENTATION_UNDEFINED
    }
}