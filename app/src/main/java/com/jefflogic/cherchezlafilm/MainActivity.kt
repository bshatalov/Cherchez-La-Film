package com.jefflogic.cherchezlafilm

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    //private var mToolbar: Toolbar? = null
    //private var mFabButton: ImageButton? = null
    private val mItemImageList: MutableList<Int>    = ArrayList()
    private val mItemTextList : MutableList<String> = ArrayList()
    private val mItemNoteList : MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mToolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(mToolbar)
        //setTitle("В одну кучку гоп")
        mToolbar.setTitleTextColor(Color.WHITE)

        //val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        // Код для адаптера. Позже
        //mFabButton = findViewById(R.id.fabButton) as ImageButton?

        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initLists();
        val recyclerAdapter = RecyclerAdapter(mItemImageList, mItemTextList, mItemNoteList);
        mRecyclerView.adapter = recyclerAdapter;
        mRecyclerView.setOnScrollListener(object : HidingScrollListener() {
            override fun onHide() {
                hideViews()
            }

            override fun onShow() {
                showViews()
            }
        })
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
        ,textResID: Int
        ,noteResID: Int
    ) {
        mItemImageList.add(imageResID)
        mItemTextList .add(getString(textResID))
        mItemNoteList .add(getString(noteResID))
    }

    private fun initLists() {
//        mItemImageList.add(R.drawable.coming_to_america_2_to_be_released_in_august_2020)
//        mItemTextList .add(R.string.coming2america_short.toString() )
//    init {
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

        add( R.drawable.french
            ,R.string  .french_short
            ,R.string  .french_long  )

        add( R.drawable.tenet
            ,R.string  .tenet_short
            ,R.string  .tenet_long  )

    }
}

/*
class MainActivity : */
/*ActionBarActivity()*//*
 AppCompatActivity() {

    private val mToolbar: Toolbar? = null
    private val mFabButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
*/
