package com.dk.artquiz.ui.artwork

import android.support.annotation.StringRes
import com.dk.artquiz.base.BaseView


/**
 * Interface providing required method for a view displaying artwork and UI Views
 */
interface ArtworkView : BaseView {

    fun updateArtwork(imageUrl: String)

    fun updateAnswers(answerA: String, answerB: String, answerC: String, answerD: String)

    fun updateView(textId: Int, showAnswers: Boolean)

    fun resetTimer()

    fun stopTimer()

    fun showError(error: String)

    fun showError(@StringRes errorResId: Int){
        this.showError(getContext().getString(errorResId))
    }

}