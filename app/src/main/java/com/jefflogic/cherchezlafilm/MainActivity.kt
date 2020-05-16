package com.jefflogic.cherchezlafilm

/* TODO
Homework 1:

+1. Создайте проект
+2. Залейте проект на GitHub
+3. Добавьте описание проекта в заголовке и Read.me
+4. На первом экране своего приложения создайте несколько картинок с фильмами. К каждой картинке добавьте название фильма и кнопку “Детали”. Изображения любые, название и описание произвольные
+5. По нажатию на Детали -
  +выделяйте другим цветом название выбранного фильма,
  +открывайте новое окно, где
  +показывайте картинку и
  +описание фильма
+6. Сохраняйте выделение фильма при повороте и при возвращении со второго экрана
+7. Добавьте кнопку “Пригласить друга” и отправляйте приглашение по вашему выбору (почта, смс, социальные сети)
8. *+Добавьте на втором экране checkbox “Нравится” и текстовое поле для комментария.
    +Возвращайте значение чекбокса и текст комментария при переходе обратно на первый экран.
    +Возвращенные значения чекбокса и текстового комментария выводим в лог
 */

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


private val TAG: String = MainActivity::class.java.simpleName

data class Item(
     val image: Int
    ,val text : Int
    ,val note : Int
) {
    var like   : Boolean? = null
    var comment: String?  = null
}


class MainActivity : AppCompatActivity(), View.OnClickListener {

    val SELECTED_POSITION_CODE = "SELECTED_POSITION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mToolbar)
        mToolbar.setTitleTextColor(Color.WHITE)

        if (itemCount == 0) initLists()

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = RecyclerAdapter()
        mRecyclerView.setOnScrollListener(object : HidingScrollListener() {
            override fun onHide() = hideViews()
            override fun onShow() = showViews()
        })

        // Восстановить состояние
        if (savedInstanceState != null) {
            RecyclerItemViewHolder.mItemSelected = savedInstanceState.getInt(SELECTED_POSITION_CODE)
        }
        mFabButton.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (lastReturnedPosition != null) {
            Log.d(TAG, "Comments: ${MainActivity.getItem(lastReturnedPosition!!).comment}")
            Log.d(TAG, "Like    : ${MainActivity.getItem(lastReturnedPosition!!).like}")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_POSITION_CODE, RecyclerItemViewHolder.mItemSelected?: -1)
    }

    // Вспомогательные методы
    private fun hideViews() {
        mToolbar.animate().translationY((-mToolbar.height).toFloat()).interpolator =
            AccelerateInterpolator(2F)
        val lp = mFabButton.layoutParams as FrameLayout.LayoutParams
        val fabBottomMargin = lp.bottomMargin
        mFabButton.animate().translationY(
            (mFabButton.height +
                    fabBottomMargin).toFloat()
        ).setInterpolator(AccelerateInterpolator(2F)).start()
    }

    private fun showViews() {
        mToolbar.animate().translationY(0F).interpolator = DecelerateInterpolator(2F)
        mFabButton.animate().translationY(0F).setInterpolator(DecelerateInterpolator(2F)).start()
    }

    private fun add(
         imageResID: Int
        ,textResID : Int
        ,noteResID : Int
    ) {
        mItemList.add(
            Item(
                imageResID
                ,textResID
                ,noteResID
            )
        )
    }

    private fun initLists() {
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mFabButton -> inviteFriends()
        }
    }

    companion object {
        private val mItemList: MutableList<Item> = ArrayList()
        fun getItem(position: Int) = mItemList[position - 1]
        val itemCount: Int
            get() = mItemList.size

        var lastReturnedPosition : Int? = null
   }

}