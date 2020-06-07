package com.jefflogic.cherchezlafilm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

private val TAG: String = DetailsActivity::class.java.simpleName

class DetailsActivity : AppCompatActivity(), View.OnClickListener{
    var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Получить переданные данные
        val intent = intent
        position = intent.getIntExtra(POSITION_CODE, -1)

        mDetailsImageView.setImageResource(MainActivity.getItem(position).image)
        mDetailsTextView.text = mDetailsTextView.context.getString(MainActivity.getItem(position).note)
        setImageLike()
        mDetailsImageViewLike.setOnClickListener(this)
        mDetailsEditTextComments.setText(MainActivity.getItem(position).comment)
    }

    fun setImageLike(){
        if (MainActivity.getItem(position).like)
            mDetailsImageViewLike.setImageResource(
                R.drawable.ic_favorite_green_24dp
            )
        else
            mDetailsImageViewLike.setImageResource(
                R.drawable.ic_favorite_border_green_24dp
            )
    }

    fun imageViewLikeClick() {
        // inversion of Like
        MainActivity.getItem(position).like = !MainActivity.getItem(position).like
        setImageLike()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mDetailsImageViewLike -> imageViewLikeClick()
        }
    }

    override fun onPause() {
        super.onPause()
        //Log.d(TAG, "onPause() text: ${mDetailsEditTextComments.text}")
        MainActivity.lastReturnedPosition = position
        MainActivity.getItem(position).comment = mDetailsEditTextComments.text.toString()
    }
}