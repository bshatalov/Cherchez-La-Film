package com.jefflogic.cherchezlafilm

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jefflogic.cherchezlafilm.App.Companion.getColumnsNum
import com.jefflogic.cherchezlafilm.App.Companion.viewHolderPositionSelected
import com.jefflogic.cherchezlafilm.App.Companion.films
import kotlinx.android.synthetic.main.fragment_film_list.*

class FilmListFragment : Fragment() {
    /////
    companion object {
        const val TAG = "FilmListFragment"
    }

    //var listener: FilmListListener? = null
    var text = "default"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate $this")

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        text = "changed"

        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy $this")
    }

    interface FilmListListener {
        fun onFilmClick(itemView: View, filmItem: FilmItem, viewHolderPosition: Int)
        fun onFilmLongClick(itemView: View, filmItem: FilmItem, viewHolderPosition: Int)
        fun onInviteFriendsClick()
        fun onNightThemeClick()
        fun onFavouritesClick()
    }

/*
    mInviteFriendsBtn?.setOnClickListener((activity as MainActivity))
    mNightThemeBtn.setOnClickListener((activity as MainActivity))
    mFavouritesBtn.setOnClickListener((activity as MainActivity))
*/

    /////

    private fun initRecycler() {
        val layoutManager = GridLayoutManager (
            this.context,
            getColumnsNum(),  /* число столбцов */
            LinearLayoutManager.VERTICAL,  /* вертикальная ориентация */
            false
        )

        mRecyclerView.layoutManager = layoutManager

        mRecyclerView.adapter = FilmItemAdapter(
            LayoutInflater.from(activity),
            films,
            {itemView, filmItem, viewHolderPosition ->
                //Toast.makeText(activity, "Adapter Click $viewHolderPosition", Toast.LENGTH_LONG)
                (activity as? FilmListListener)?.onFilmClick(itemView, filmItem, viewHolderPosition)
                //listener?.onFilmSelected(itemView, filmItem, viewHolderPosition)
            },
            { itemView, filmItem, viewHolderPosition ->
                //Toast.makeText(activity, "Adapter LongClick $viewHolderPosition", Toast.LENGTH_LONG)
                (activity as? FilmListListener)?.onFilmLongClick(itemView, filmItem, viewHolderPosition)
                true
            }
        )

        initClickListeners()

/*
        mRecyclerView.adapter = FilmItemAdapter(
            LayoutInflater.from(activity),
            films,
            //ClickListener: to mItemDetailsButton
            { itemView, filmItem, position ->
                //itemDetailsClick(itemView, filmItem, position)
            },
            //LongClickListener:
            { itemView, filmItem, position ->
                // new item is selected
                itemSelect(itemView, filmItem, position)
                setImageLike(itemView, filmItem, position)
                mRecyclerView.adapter?.notifyItemChanged(position)
                Log.d(TAG, "longClickListener: notifyItemChanged at position $position")
                false
            }
        )
*/

        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == films.size) {
                    repeat(4) {
                        App.addRandom()
                    }
                    recyclerView.adapter?.notifyItemRangeInserted(films.size - 3, 4)
                }
            }
        })
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        mRecyclerView.addItemDecoration(itemDecoration)

        mSwipeRefreshLayout.setOnRefreshListener {
            // Hide swipe to refresh icon animation
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    /*
    private fun itemDetailsClick(itemView: View, filmItem: FilmItem, position: Int) {
        // new item is selected
        itemSelect(itemView, filmItem, position)

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MainActivity.POSITION_CODE, position)
        ActivityCompat.startActivityForResult(this, intent, MainActivity.REQUEST_CODE_LIKE, null)
    }
    */

    override fun onResume() {
        super.onResume()
        /*if (mLastReturnedPosition != null) {
            Log.d(TAG, "Comments: ${mFilms[mLastReturnedPosition!!].comment}")
            Log.d(TAG, "Like    : ${mFilms[mLastReturnedPosition!!].like}")
        }
        */
        // Обновить mRecyclerView при возврате
        if (viewHolderPositionSelected != null) {
            mRecyclerView.adapter?.notifyItemChanged(viewHolderPositionSelected!!)
            //Log.d(TAG, "onResume-notifyItemChanged at position $mItemSelected")
        }
        mRecyclerView.adapter?.notifyDataSetChanged()
    }

    private class CustomItemDecoration(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {
        override fun onDraw(c: Canvas, parent: RecyclerView) {
            super.onDraw(c, parent)
        }

        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            super.getItemOffsets(outRect, itemPosition, parent)
        }
    }

    private fun initClickListeners(){
        //(activity as? FilmListListener)?.onFilmLongClick(itemView, filmItem, viewHolderPosition)
        mInviteFriendsBtn?.setOnClickListener{ (activity as? FilmListListener)?.onInviteFriendsClick() }
        mNightThemeBtn.setOnClickListener{ (activity as? FilmListListener)?.onNightThemeClick() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            mNightThemeBtn.visibility = View.VISIBLE
        } else {
            mNightThemeBtn.visibility = View.GONE
        }
        mFavouritesBtn.setOnClickListener{ (activity as? FilmListListener)?.onFavouritesClick() }
    }
}