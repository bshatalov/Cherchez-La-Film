package com.jefflogic.cherchezlafilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favourite.*
import java.util.ArrayList

private val TAG: String = FavouriteActivity::class.java.simpleName

class FilmFavouritesFragment : Fragment()  {

    companion object {
        const val TAG = "FilmFavouritesFragment"
        //private var orientation: Int = Configuration.ORIENTATION_UNDEFINED

        fun newInstance(): FilmFavouritesFragment {
            val fragment = FilmFavouritesFragment()
            //val bundle = Bundle()
            //bundle.putInt(FilmFavouritesFragment.VIEW_HOLDER_POSITION, viewHolderposition)
            //fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_favourite)
        Log.d(TAG, "onCreate")

        App.initFavouriteItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(FilmListFragment.TAG, "onViewCreated")

        initRecycler()
    }

    private fun initRecycler() {
        val layoutManager = GridLayoutManager(
            this.context,
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
            LayoutInflater.from(activity),
            App.favouriteFilms,
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

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
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

}