package com.jefflogic.cherchezlafilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_details.*

class FilmDetailsFragment : Fragment() , View.OnClickListener {
    companion object {
        const val TAG = "FilmDetailsFragment"
        const val VIEW_HOLDER_POSITION = "VIEW_HOLDER_POSITION"
        //var listener: FilmListFragment.FilmListListener? = null

        fun newInstance(viewHolderposition: Int): FilmDetailsFragment {
            val fragment = FilmDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(VIEW_HOLDER_POSITION, viewHolderposition)
            fragment.arguments = bundle

            return fragment
/*
            return FilmDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TITLE, text)
                }
            }
*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d(FilmListFragment.TAG, "onCreate $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(view as TextView).text = arguments?.getString(EXTRA_TITLE, "default")
        // Получить переданные данные
        arguments?.getInt(VIEW_HOLDER_POSITION)?.let {
            mDetailsImageView.setImageResource(App.getItem(it).imageRes)
            mDetailsTextView.text = mDetailsTextView.context.getString(App.getItem(it).noteRes)
            setImageLike()
            mDetailsImageViewLike.setOnClickListener(this)
            mDetailsEditTextComments.setText(App.getItem(it).comment)
        }
    }

    private fun setImageLike(){
        if (App.getFilmSelected().like)
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
        App.getFilmSelected().like = !App.getFilmSelected().like
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
        App.getFilmSelected().comment = mDetailsEditTextComments.text.toString()
    }
}