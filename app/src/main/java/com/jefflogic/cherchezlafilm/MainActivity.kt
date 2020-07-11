package com.jefflogic.cherchezlafilm

/* TODO
Homework 8: Fragments & Navigation
--
+0.Вынести всю логику из MainActivity во фрагменты
+MainActivity - вся логика навигации (открытие, закрытие, ...)
+00. Данные хранить на уровне класса app!! Чтобы они переживали всякие повороты и тд

--
+1. Переведите свое приложение на единственную Activity и несколько фрагментов
2. Для навигации между фрагментами используйте NavigationDrawer или BottomNavigation
3. Добавьте CoordinatorLayout + CollapsingToolbar на детальный экран фильма
4. Добавьте Snackbar (должен отображаться выше!! чем BottomNavigation) или Toast, сообщающий об успехе добавления\удаления из избранного
5. * Добавьте возможность отмены действия в snackbar

P.S. Задание со звездочкой * - повышенной сложности. Если вы с ним не справитесь - ничего страшного, оно не является обязательным.
Рекомендуем сдать до: 11.05.2020
*/

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_film_list.*
import kotlinx.android.synthetic.main.item_film.view.*


class MainActivity : AppCompatActivity(), View.OnClickListener, FilmListFragment.FilmListListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        //App.orientation = resources.configuration.orientation

        //App.initItems()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mFragmentContainer, FilmListFragment(), FilmListFragment.TAG)
            .commit()

/*
        initRecycler()
        initClickListeners()
*/

        // Восстановить состояние
        if (savedInstanceState != null) {
            App.viewHolderPositionSelected =  savedInstanceState.getInt(SELECTED_POSITION_CODE)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

/*
        if (fragment is FilmListFragment) {
            fragment.listener = this
        }
*/
    }

     private fun openDetailsFragment(itemView: View, filmItem: FilmItem, viewHolderPosition: Int) {
        selectItem(itemView, filmItem, viewHolderPosition)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mFragmentContainer, FilmDetailsFragment.newInstance(viewHolderPosition), FilmDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    private fun likeItem(itemView: View, filmItem: FilmItem, viewHolderposition: Int) {
        // new item is selected
        selectItem(itemView, filmItem, viewHolderposition)
        changeImageLike(itemView, filmItem, viewHolderposition)
        mRecyclerView.adapter?.notifyItemChanged(viewHolderposition)
        Log.d(TAG, "longClickListener: notifyItemChanged at position $viewHolderposition")
        false
    }


    /*
    private fun itemDetailsClick(itemView: View, filmItem: FilmItem, position: Int) {
        // new item is selected
        itemSelect(itemView, filmItem, position)
    }
    */

    private fun changeImageLike(itemView: View, filmItem: FilmItem, viewHolderposition: Int) {
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

    private fun selectItem(itemView: View, filmItem: FilmItem, viewHolderPosition: Int) {
        val prevPosition: Int? = App.viewHolderPositionSelected

        // previous item -> unHighlight
        App.filmTextViewSelected?.highlight(false)

        // previous position
        if (prevPosition != null) {
            mRecyclerView.adapter?.notifyItemChanged(prevPosition)
            Log.d(FilmListFragment.TAG, "itemSelect-notifyItemChanged at position $prevPosition")
        };

        // save new selected item
        App.filmTextViewSelected = itemView.mItemTextView
        App.viewHolderPositionSelected = viewHolderPosition

        // new textView -> highlight
        App.filmTextViewSelected?.highlight(true)

        // notify adapter about changes
        // new position
        mRecyclerView.adapter?.notifyItemChanged(viewHolderPosition)
        Log.d(FilmListFragment.TAG, "itemSelect-notifyItemChanged at position $viewHolderPosition")
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        openQuitDialog()
    }

    private fun openQuitDialog() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val quitDialog = AlertDialog.Builder(
                this@MainActivity
            )
            quitDialog.setTitle("Выход: Вы уверены?")
            quitDialog.setPositiveButton("Таки да!"
            ) { dialog, which -> finish() }
            quitDialog.setNegativeButton("Нет"
            ) { dialog, which -> }
            quitDialog.show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_POSITION_CODE, App.viewHolderPositionSelected?: -1)
    }

    private fun inviteFriends() {
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
        //val intent = Intent(this, FavouriteActivity::class.java)
        //startActivity(intent)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mFragmentContainer, FilmFavouritesFragment.newInstance(), FilmDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mInviteFriendsBtn  -> inviteFriends()
            R.id.mNightThemeBtn     -> changeTheme()
            R.id.mFavouritesBtn     -> showFavourites()
        }
    }


    companion object {

        private const val TAG: String = "MainActivity"
        const val POSITION_CODE = "POSITION"
        const val REQUEST_CODE_LIKE = 1
        const val SELECTED_POSITION_CODE = "SELECTED_POSITION"

    }

    override fun onFilmClick(itemView: View, filmItem: FilmItem, viewHolderPosition: Int) {
        Log.d(TAG, "onFilmClick viewHolderPosition = $viewHolderPosition")
        openDetailsFragment(itemView, filmItem, viewHolderPosition)
    }

    override fun onFilmLongClick(itemView: View, filmItem: FilmItem, viewHolderPosition: Int) {
        Log.d(TAG, "onFilmLongClick viewHolderPosition = $viewHolderPosition")
        likeItem(itemView, filmItem, viewHolderPosition)
    }

    override fun onInviteFriendsClick() {
        inviteFriends()
    }

    override fun onNightThemeClick() {
        changeTheme()
    }

    override fun onFavouritesClick() {
        showFavourites()
    }

}