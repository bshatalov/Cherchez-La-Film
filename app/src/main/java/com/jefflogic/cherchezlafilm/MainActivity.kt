package com.jefflogic.cherchezlafilm

/* TODO
Homework 5:

+1. Создайте различные стили для текста заголовка и описания
+2. Используйте стили на экране со списком и детальном экране
+3. Добавьте поддержку английского и русского языков для элементов интерфейса, например, для кнопки "детали" и "пригласить друга"
+4. Используйте векторное изображение из стандартного набора для кнопки пригласить друга
+5. Добавьте поддержку альбомной ориентации. Интерфейс должен отличаться. Например, в портретной 2 фильма в строке списка, а в альбомной 4
+6. Создайте кастомный диалог подтверждения при выходе из приложения при нажатии кнопки back (использовать метод onBackPressed)
+7. * Добавьте кнопку переключения темы в приложении, например дневной\ночной

*/

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
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

        orientation = getResources().getConfiguration().orientation

        setSupportActionBar(mToolbar)
        mToolbar.setTitleTextColor(Color.WHITE)

        if (itemCount == 0) initLists()

        val gridLayoutManagerLand = GridLayoutManager(
            this,
            2,  /* число столбцов */
            LinearLayoutManager.VERTICAL,  /* вертикальная ориентация */
            false
        )

        val gridLayoutManagerPort = GridLayoutManager(
            this,
            1,  /* число столбцов */
            LinearLayoutManager.VERTICAL,  /* вертикальная ориентация */
            false
        )

        when(orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                mRecyclerView.layoutManager = gridLayoutManagerPort
            Configuration.ORIENTATION_LANDSCAPE ->
                mRecyclerView.layoutManager = gridLayoutManagerLand
        }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            mFabButton.visibility = View.VISIBLE
        } else {
            mFabButton.visibility = View.GONE
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }

    override fun onResume() {
        super.onResume()
        if (lastReturnedPosition != null) {
            Log.d(TAG, "Comments: ${MainActivity.getItem(lastReturnedPosition!!).comment}")
            Log.d(TAG, "Like    : ${MainActivity.getItem(lastReturnedPosition!!).like}")
        }
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

    private fun changeTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            /*R.id.mFabButton -> inviteFriends()*/
            R.id.mFabButton -> changeTheme()
        }
    }

    companion object {
        private val mItemList: MutableList<Item> = ArrayList()
        var orientation: Int = Configuration.ORIENTATION_UNDEFINED

        fun getItem(position: Int): Item {
            /*Log.d(TAG, "getItem orientation = $orientation position = $position")*/

            return when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> mItemList[position - 1]
                Configuration.ORIENTATION_LANDSCAPE -> mItemList[position - 2]
                else -> mItemList[position - 1]
            }
        }

        val itemCount: Int
            get() {
                /*Log.d(TAG, "itemCount = ${mItemList.size}")*/
                return when (orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> return mItemList.size
                    Configuration.ORIENTATION_LANDSCAPE -> return mItemList.size + 1
                    else -> return mItemList.size
                }
            }

        var lastReturnedPosition : Int? = null
   }

}