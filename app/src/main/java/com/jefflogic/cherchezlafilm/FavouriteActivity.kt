package com.jefflogic.cherchezlafilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

    private fun initItems() {
        // fill only favourite items from MainActivity.items
        Log.d(TAG, "initItems()")
        items.clear()
        for(i in 0 until App.films.size){
            if (App.films[i].like) {
                Log.d(TAG, "i=$i")
                val item = App.films[i]
                val favFilmItem = FilmItem(
                     item.imageRes
                    ,item.textRes
                    ,item.noteRes
                    ,item.textStr
                    ,i  // parent item
                )
                favFilmItem.like = true
                items.add(favFilmItem)
            }
        }
    }

    private fun initRecycler() {
        val layoutManager = GridLayoutManager(
            this,
            App.getColumnsNum(),
/*
            when(orientation) {
                Configuration.ORIENTATION_PORTRAIT -> MainActivity.PORTRAIT_COLUMNS
                Configuration.ORIENTATION_LANDSCAPE -> MainActivity.LANDSCAPE_COLUMNS
                else -> MainActivity.PORTRAIT_COLUMNS
            },
*/
            /* число столбцов */
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
    private fun itemUnlikeClick(filmItem: FilmItem, position: Int) {
        App.films[filmItem.parent!!].like = false
        if (mFavRecyclerView.adapter is FilmItemAdapter) {
            (mFavRecyclerView.adapter as FilmItemAdapter).removeAt(position)
        }
    }

    companion object {
        private val items: MutableList<FilmItem> = ArrayList()
        //private var orientation: Int = Configuration.ORIENTATION_UNDEFINED
    }
}