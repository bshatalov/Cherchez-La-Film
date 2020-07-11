package com.jefflogic.cherchezlafilm

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.ArrayList

class App : Application() {
    companion object {
        private val TAG: String = "App"

        const val PORTRAIT_COLUMNS  = 1
        const val LANDSCAPE_COLUMNS = 2

        var context: Context? = null

        /*private*/ val films           : MutableList<FilmItem> = ArrayList()
        /*private*/ val favouriteFilms      : MutableList<FilmItem> = ArrayList()
        //    fun getFilms() = mFilms

        /*private*/ var orientation : Int? = Configuration.ORIENTATION_UNDEFINED
            get() = context?.resources?.configuration?.orientation

        //    fun setOrientation(orientation: Int) { mOrientation = orientation}
        //    fun getOrientation() = mOrientation

        /*private*/ var viewHolderPositionSelected : Int? = null     //chosen position
        //    fun getFilmPosSelected() = mFilmPosSelected
        //    fun setFilmPosSelected(position: Int) { mFilmPosSelected = position }

        fun getFilmSelected() = films[getItemRealPos(viewHolderPositionSelected!!)]

        /*private*/ var filmTextViewSelected: TextView? = null
        //    fun getFilmTextViewSelected() = mFilmTextViewSelected
        //    fun setFilmTextViewSelected(textView: TextView) {mFilmTextViewSelected = textView}

//        private var mLastReturnedPosition: Int? = null
//            fun setLastReturnedPosition(position: Int) { mLastReturnedPosition = position }

        //viewHolderPosition - position in ViewHolder
        //getItem returns item in [items] array by its ViewHolder position
        fun getItem(viewHolderPosition: Int): FilmItem {
            return films[getItemRealPos(viewHolderPosition)]
        }

        //viewHolderPosition - position in ViewHolder

        //getItemRealPos returns real position of item in [items] array by ViewHolder position
        fun getItemRealPos(viewHolderPosition: Int): Int {
            return viewHolderPosition - getColumnsNum()
        }

        fun getColumnsNum() : Int {
            return when (orientation) {    // + header: 1 for portrait, 2 for landscape
                Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMNS
                Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMNS
                else -> PORTRAIT_COLUMNS
            }
        }

        fun addRandom() {
            addElse((Math.random() * 10).toInt() + getColumnsNum())
        }

        private fun addElse(position: Int) {
            add(
                 films[position].imageRes
                ,films[position].textRes
                ,films[position].noteRes
            )
        }

        private fun add(
            imageResID: Int
            ,textResID : Int
            ,noteResID : Int
        ) {
            films.add(
                FilmItem(
                    imageResID
                    ,textResID
                    ,noteResID
                    ,context?.getString(textResID) + " " + films.size
                )
            )
        }

        fun initItems() {
            add( R.drawable.coming_to_america_2_to_be_released_in_august_2020
                ,R.string.coming2america_short
                ,R.string.coming2america_long )

            add( R.drawable.david_kop
                ,R.string  .david_kop_short
                ,R.string  .david_kop_long  )

            add( R.drawable.emma
                ,R.string  .emma_short
                ,R.string  .emma_long  )

            add( R.drawable.freeguy
                ,R.string  .freeguy_short
                ,R.string  .freeguy_long  )

            add( R.drawable.soul
                ,R.string  .soul_short
                ,R.string  .soul_long  )

            add( R.drawable.topgun
                ,R.string  .topgun_short
                ,R.string  .topgun_long  )

            add( R.drawable.kingsman
                ,R.string  .kingsman_short
                ,R.string  .kingsman_long  )

            add( R.drawable.french
                ,R.string  .french_short
                ,R.string  .french_long  )

            add( R.drawable.tenet
                ,R.string  .tenet_short
                ,R.string  .tenet_long  )

            add( R.drawable.bond
                ,R.string  .bond_short
                ,R.string  .bond_long  )
        }

        /*private*/ fun initFavouriteItems() {
            // fill only favourite items from MainActivity.items
            //Log.d(FilmFavouritesFragment.TAG, "initFavouriteItems()")
            favouriteFilms.clear()
            for(i in 0 until films.size){
                if (films[i].like) {
                    //Log.d(FilmFavouritesFragment.TAG, "i=$i")
                    val film = films[i]
                    val favouriteFilm = FilmItem(
                         film.imageRes
                        ,film.textRes
                        ,film.noteRes
                        ,film.textStr
                        ,i  // parent item
                    )
                    favouriteFilm.like = true
                    favouriteFilms.add(favouriteFilm)
                }
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        context = applicationContext

        initItems()
    }


}

// highlight the view
fun TextView.highlight(
    highlight: Boolean
) {
    if (highlight)
        this.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent))
    else
        this.setTextColor(ContextCompat.getColor(this.context, R.color.black))
}

