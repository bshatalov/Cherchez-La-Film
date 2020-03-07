package com.jefflogic.cherchezlafilm

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    //private var mToolbar: Toolbar? = null
    //private var mFabButton: ImageButton? = null

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
        val recyclerAdapter = RecyclerAdapter(createItemList());
        mRecyclerView.adapter = recyclerAdapter;
    }

// Вспомогательный метод для подготовки списка
    open fun createItemList(): MutableList<String> {
        val itemList: MutableList<String> = ArrayList()
        for (i in 0..29) {
            itemList.add("Кот $i")
        }
        return itemList
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
