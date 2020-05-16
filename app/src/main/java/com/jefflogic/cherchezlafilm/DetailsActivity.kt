package com.jefflogic.cherchezlafilm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

/*
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_details.*
*/

private val TAG: String = DetailsActivity::class.java.simpleName

class DetailsActivity : AppCompatActivity(), View.OnClickListener{
    var position = -1

    //private var intentResult: Intent? = null
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

/*
        mDetailsImageView.setImageResource(MainActivity.getItemImageResId(position))
        mDetailsTextView.text = mDetailsTextView.context.getString(MainActivity.getItemNoteResId(position))
*/

        /*
        if (intentResult == null) {
            intentResult = Intent()
        }
        mButtonOk = findViewById(R.id.buttonOk)
        mButtonOk.setOnClickListener(View.OnClickListener {
            intentResult!!.putExtra(
                MainActivity.commentsCode,
                mTextViewComments.getText().toString()
            )
            intentResult!!.putExtra(MainActivity.likeCode, mCheckBoxLike.isChecked())
            setResult(Activity.RESULT_OK, intentResult)
            finish()
        }
        )
         */
    }

    fun setImageLike(){
        if (!(MainActivity.getItem(position).like ?: false))
            // Like = null or false
            mDetailsImageViewLike.setImageResource(
                R.drawable.ic_favorite_border_black_24dp
            )
        else
            // Like = true
            mDetailsImageViewLike.setImageResource(
                R.drawable.ic_favorite_black_24dp
            )
    }

    fun imageViewLikeClick() {
        // inversion of Like
        MainActivity.getItem(position).like = !(MainActivity.getItem(position).like ?: false)
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