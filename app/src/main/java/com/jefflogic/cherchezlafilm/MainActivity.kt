package com.jefflogic.cherchezlafilm

/* TODO
Homework 6: RecyclerView

Работа со списками.
+1. Переведите ваше приложение на отображение списков с помощью RecyclerView
2. +Дополните функционал вашего приложения сохранением фильмов в список избранного
   +(избранное пока храните в обычном List на уровне Activity).
   +> Используйте для этого или долгое нажатие на элемент списка,
   --или тап на ImageView в виде сердечка рядом с названием фильма
3. +Создайте экран, где будет отображаться список Избранного
4. +Сделайте так, чтобы в список Избранного можно было !!удалять элементы (и, если получится, добавлять элементы)
5. +*Написать собственный ItemDecoration
6. -* Самостоятельно изучите RecyclerView.ItemAnimator, создайте свои собственные анимации
*/

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_film.view.*
import java.util.*


private val TAG: String = MainActivity::class.java.simpleName
val POSITION_CODE = "POSITION"
val REQUEST_CODE_LIKE = 1
val SELECTED_POSITION_CODE = "SELECTED_POSITION"
val PORTRAIT_COLUMNS = 1
val LANDSCAPE_COLUMNS = 2

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orientation = getResources().getConfiguration().orientation

        if (items.size == 0) initItems()

        initRecycler()
        initClickListeners()

        // Восстановить состояние
        if (savedInstanceState != null) {
            mItemSelected = savedInstanceState.getInt(SELECTED_POSITION_CODE)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }


    fun initRecycler() {
        val layoutManager = GridLayoutManager(
            this,
            when(orientation) {Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMNS; Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMNS; else -> PORTRAIT_COLUMNS},  /* число столбцов */
            LinearLayoutManager.VERTICAL,  /* вертикальная ориентация */
            false
        )

        mRecyclerView.layoutManager = layoutManager

        mRecyclerView.adapter = FilmItemAdapter(LayoutInflater.from(this), items,
            //ClickListener: to mItemDetailsButton
            { itemView, filmItem, position ->
                itemDetailsClick(itemView, filmItem, position)
            },
            //LongClickListener:
            { itemView, filmItem, position ->
                setImageLike(itemView, filmItem, position)
                mRecyclerView.adapter?.notifyItemChanged(position)
                Log.d(TAG, "longClickListener: notifyItemChanged at position $position")
                false
            }
        )

        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == items.size) {
                    repeat(4) {
                        addRandom()
                    }
                    recyclerView.adapter?.notifyItemRangeInserted(items.size - 3, 4)
                }
            }
        })
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mRecyclerView.addItemDecoration(itemDecoration)

        mSwipeRefreshLayout.setOnRefreshListener {
            // Hide swipe to refresh icon animation
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    // highlight the view
    private fun TextView.highlight(
        highlight: Boolean
    ) {
        if (highlight)
            this.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent))
        else
            this.setTextColor(ContextCompat.getColor(this.context, R.color.black))
    }

    private fun itemSelect(itemView: View, filmItem: FilmItem, position: Int) {
        val prevPosition: Int? = mItemSelected

        // previous item -> unHighlight
        mItemTextViewSelected?.highlight(false)

        // save new selected item
        mItemTextViewSelected = itemView.mItemTextView
        mItemSelected     = position

        // new textView -> highlight
        mItemTextViewSelected?.highlight(true)

        // notify adapter about changes
        // previous position
        if (prevPosition != null) {
            mRecyclerView.adapter?.notifyItemChanged(prevPosition)
            Log.d(TAG, "itemSelect-notifyItemChanged at position $prevPosition")
        };
        // new position
        mRecyclerView.adapter?.notifyItemChanged(position)
        Log.d(TAG, "itemSelect-notifyItemChanged at position $position")
    }

    private fun itemDetailsClick(itemView: View, filmItem: FilmItem, position: Int) {
        // new item is selected
        itemSelect(itemView, filmItem, position)

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(POSITION_CODE, position)
        startActivityForResult(this, intent, REQUEST_CODE_LIKE, null)
    }


    fun setImageLike(itemView: View, filmItem: FilmItem, position: Int) {
        if (filmItem.like) {
            filmItem.like = false
            itemView.mItemImageViewLike.setImageResource(
                0
                //R.drawable.ic_favorite_border_black_24dp
            )
        } else {
            filmItem.like = true
            itemView.mItemImageViewLike.setImageResource(
                R.drawable.ic_favorite_green_24dp
            )
        }
    }

    class CustomItemDecoration(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {
        override fun onDraw(c: Canvas, parent: RecyclerView) {
            super.onDraw(c, parent)
        }

        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            super.getItemOffsets(outRect, itemPosition, parent)
        }
    }

    fun initClickListeners(){
        mInviteFriendsBtn.setOnClickListener(this)
        mNightThemeBtn.setOnClickListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            mNightThemeBtn.visibility = View.VISIBLE
        } else {
            mNightThemeBtn.visibility = View.GONE
        }
        mFavouritesBtn.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (lastReturnedPosition != null) {
            Log.d(TAG, "Comments: ${items[lastReturnedPosition!!].comment}")
            Log.d(TAG, "Like    : ${items[lastReturnedPosition!!].like}")
        }
        // Обновить mRecyclerView при возврате
        if (mItemSelected != null) {
            mRecyclerView.adapter?.notifyItemChanged(mItemSelected!!)
            //Log.d(TAG, "onResume-notifyItemChanged at position $mItemSelected")
        }
        mRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        openQuitDialog()
    }

    private fun openQuitDialog() {
        val quitDialog = AlertDialog.Builder(
            this@MainActivity
        )
        quitDialog.setTitle("Выход: Вы уверены?")
        quitDialog.setPositiveButton("Таки да!", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                finish()
            }
        })
        quitDialog.setNegativeButton("Нет", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        quitDialog.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_POSITION_CODE, mItemSelected?: -1)
    }

    private fun addRandom() {
        addElse((Math.random() * 10).toInt() + getColumnsNum())
    }

    private fun addElse(position: Int) {
        add(
             items[position].image
            ,items[position].text
            ,items[position].note
        )
    }

    private fun add(
         imageResID: Int
        ,textResID : Int
        ,noteResID : Int
    ) {
        items.add(
            FilmItem(
                 imageResID
                ,textResID
                ,this.getString(textResID) + " " + items.size
                ,noteResID
            )
        )
    }

    private fun initItems() {
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

    fun inviteFriends() {
        val textMessage = "Установите наше приложение: Cherchez La Film - Ищите Фильм!"
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage)
        sendIntent.type = "text/plain"

        val title = resources.getString(R.string.chooser_title)
        // Создаем Intent для отображения диалога выбора.
        val chooser = Intent.createChooser(sendIntent, title)

        // Проверяем, что intent может быть успешно обработан
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

    private fun changeTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun showFavourites() {
        val intent = Intent(this, FavouriteActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mInviteFriendsBtn  -> inviteFriends()
            R.id.mNightThemeBtn     -> changeTheme()
            R.id.mFavouritesBtn     -> showFavourites()
        }
    }

    companion object {
        val items                : MutableList<FilmItem> = ArrayList()
        var orientation          : Int = Configuration.ORIENTATION_UNDEFINED
        var mItemSelected        : Int? = null     //choosen position
        var mItemTextViewSelected: TextView? = null

        fun getColumnsNum() : Int {
            return when (orientation) {    // + header: 1 for portrait, 2 for landscape
                Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMNS
                Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMNS
                else -> PORTRAIT_COLUMNS
            }
        }
        fun getItemPos(position: Int): Int {
            return position - getColumnsNum()
        }
        fun getItem(position: Int): FilmItem {
            return items[getItemPos(position)]
        }

        var lastReturnedPosition : Int? = null
    }

}