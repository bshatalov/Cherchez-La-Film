package com.jefflogic.cherchezlafilm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jefflogic.cherchezlafilm.App.Companion.getFilmSelected
import kotlinx.android.synthetic.main.activity_details.*

private val TAG: String = DetailsActivity::class.java.simpleName

class DetailsActivity : AppCompatActivity(), View.OnClickListener{
    //private var mPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Получить переданные данные
        val intent = intent
        //mPosition = intent.getIntExtra(MainActivity.POSITION_CODE, -1)

        mDetailsImageView.setImageResource(getFilmSelected().imageRes)
        mDetailsTextView.text = mDetailsTextView.context.getString(getFilmSelected().noteRes)
        setImageLike()
        mDetailsImageViewLike.setOnClickListener(this)
        mDetailsEditTextComments.setText(getFilmSelected().comment)
    }


    private fun setImageLike(){
        if (getFilmSelected().like)
            mDetailsImageViewLike.setImageResource(
                R.drawable.ic_favorite_green_24dp
            )
        else
            mDetailsImageViewLike.setImageResource(
                R.drawable.ic_favorite_border_green_24dp
            )
    }

    private fun imageViewLikeClick() {
        // inversion of Like
        getFilmSelected().like = !getFilmSelected().like
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
        //setLastReturnedPosition(mPosition)
        getFilmSelected().comment = mDetailsEditTextComments.text.toString()
    }
}