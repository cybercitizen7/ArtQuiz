package com.dk.artquiz

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.dk.artquiz.base.BaseActivity
import com.dk.artquiz.ui.artwork.ArtworkPresenter
import com.dk.artquiz.ui.artwork.ArtworkView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class ArtworkActivity : BaseActivity<ArtworkPresenter>(), ArtworkView, View.OnClickListener {

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var objectAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initiate on click listeners for our buttons
        a_button.setOnClickListener(this)
        b_button.setOnClickListener(this)
        c_button.setOnClickListener(this)
        d_button.setOnClickListener(this)
        tryagain_button.setOnClickListener(this)

        // Notify presenter that view was created
        presenter.onViewCreated()
    }


    override fun resetTimer() {
        // User Object Animator for Smooth Scrolling
        objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 100, 0)
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 10000
        objectAnimator.start()

        // Using CountDown Timer to determine when the time to answer is finished
        countDownTimer = object: CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                presenter.handleTimeOut()
            }
        }.start()
    }

    override fun stopTimer() {
        countDownTimer.cancel()
        objectAnimator.cancel()
    }

    override fun updateView(textId: Int, showAnswers: Boolean) {
        // Update narrator text
        narator_text.text = this.resources.getString(textId)

        // Depending on game state, we either show answer buttons or Try again button
        if (showAnswers) {
            a_button.visibility = VISIBLE
            b_button.visibility = VISIBLE
            c_button.visibility = VISIBLE
            d_button.visibility = VISIBLE

            tryagain_button.visibility = GONE
        } else {
            a_button.visibility = GONE
            b_button.visibility = GONE
            c_button.visibility = GONE
            d_button.visibility = GONE

            tryagain_button.visibility = VISIBLE
        }
    }

    override fun updateAnswers(answerA: String, answerB: String, answerC: String, answerD: String) {
        a_button.text = answerA
        b_button.text = answerB
        c_button.text = answerC
        d_button.text = answerD
    }

    override fun updateArtwork(imageUrl: String) {
        // Download the image to its View
        Picasso.get().load(imageUrl).into(artwork_image)
    }

    override fun showError(error: String) {
        Log.e("ARTQUIZ-ERROR", "ERROR ON DOWNLOAD: $error")
        Toast.makeText(this,"ERROR ON DOWNLOAD: $error", Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            a_button.id -> {presenter.handleClickedAnswer(a_button.text.toString())}
            b_button.id -> {presenter.handleClickedAnswer(b_button.text.toString())}
            c_button.id -> {presenter.handleClickedAnswer(c_button.text.toString())}
            d_button.id -> {presenter.handleClickedAnswer(d_button.text.toString())}
            else -> { presenter.initGame() }
        }
    }

    override fun instantiatePresenter(): ArtworkPresenter {
        return ArtworkPresenter(this)
    }

}