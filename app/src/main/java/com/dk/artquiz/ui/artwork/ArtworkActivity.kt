package com.dk.artquiz.ui.artwork

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.dk.artquiz.R
import com.dk.artquiz.base.BaseActivity
import com.dk.artquiz.utilities.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class ArtworkActivity : BaseActivity<ArtworkPresenter>(), ArtworkView, View.OnClickListener {


    private lateinit var objectAnimator: ObjectAnimator
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initiate on click listeners for our buttons
        aAnswerButton.setOnClickListener(this)
        bAnswerButton.setOnClickListener(this)
        cAnswerButton.setOnClickListener(this)
        dAnswerButton.setOnClickListener(this)
        tryagain_button.setOnClickListener(this)

        // Notify presenter that view was created
        presenter.onViewCreated()
    }


    override fun resetTimer() {
        // User Object Animator for Smooth Scrolling
        objectAnimator = ObjectAnimator.ofInt(progressBar, OBJECT_ANIMATOR_PROPERTY_NAME, MAX_PROGRESS_BAR_VALUE, 0)
        objectAnimator.start()
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = TIME_TO_ANSWER_IN_MS
        objectAnimator.start()

        // Using CountDown Timer to determine when the time to answer is finished
        countDownTimer = object: CountDownTimer(TIME_TO_ANSWER_IN_MS, TICK_TIME_IN_MS) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                presenter.handleTimeOut()
            }
        }.start()
    }

    override fun stopTimer() {
        countDownTimer?.cancel()
        objectAnimator.cancel()
    }

    override fun updateView(textId: Int, showAnswers: Boolean) {
        // Update narrator text
        narator_text.text = this.resources.getString(textId)

        // Depending on game state, we either show answer buttons or Try again button
        if (showAnswers) {
            aAnswerButton.visibility = VISIBLE
            bAnswerButton.visibility = VISIBLE
            cAnswerButton.visibility = VISIBLE
            dAnswerButton.visibility = VISIBLE

            tryagain_button.visibility = GONE
        } else {
            aAnswerButton.visibility = GONE
            bAnswerButton.visibility = GONE
            cAnswerButton.visibility = GONE
            dAnswerButton.visibility = GONE

            tryagain_button.visibility = VISIBLE
        }
    }

    override fun updateAnswers(answerA: String, answerB: String, answerC: String, answerD: String) {
        aAnswerButton.text = answerA
        bAnswerButton.text = answerB
        cAnswerButton.text = answerC
        dAnswerButton.text = answerD
    }

    override fun updateArtwork(imageUrl: String) {
        // Download the image to its View
        Picasso.get().load(imageUrl).into(artwork_image)
    }

    override fun showError(error: String) {
        Log.e(APP_DEBUG_TAG, APP_ERROR_RESPONE + error)
        Toast.makeText(this, APP_ERROR_RESPONE + error, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            aAnswerButton.id -> {presenter.handleClickedAnswer(aAnswerButton.text.toString())}
            bAnswerButton.id -> {presenter.handleClickedAnswer(bAnswerButton.text.toString())}
            cAnswerButton.id -> {presenter.handleClickedAnswer(cAnswerButton.text.toString())}
            dAnswerButton.id -> {presenter.handleClickedAnswer(dAnswerButton.text.toString())}
            else -> { presenter.initGame() }
        }
    }

    override fun instantiatePresenter(): ArtworkPresenter {
        return ArtworkPresenter(this)
    }

}