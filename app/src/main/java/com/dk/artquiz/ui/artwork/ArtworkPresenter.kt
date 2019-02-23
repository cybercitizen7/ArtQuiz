package com.dk.artquiz.ui.artwork

import com.dk.artquiz.R
import com.dk.artquiz.base.BasePresenter
import com.dk.artquiz.model.ArtworkModel
import com.dk.artquiz.network.ArtworkApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random


class ArtworkPresenter(artworkView: ArtworkView) : BasePresenter<ArtworkView>(artworkView) {
    @Inject
    lateinit var artworkApi: ArtworkApi

    private lateinit var correctAnswer: String
    private var randomNumber: Int = 0
    private var subscription: Disposable? = null

    // Picasso, Rembrandt, De Goya, Da Vinci
    // I need to use unique IDs for the Artsy API instead of artist names, hence the HashMap
    private val artistList = hashMapOf(
        1 to listOf("Pablo Picasso", "4d8b928b4eb68a1b2c0001f2"),
        2 to listOf("Rembrandt", "4d8b929c4eb68a1b2c0002e2"),
        3 to listOf("De Goya", "4d8b92b44eb68a1b2c0003fe"),
        4 to listOf("Leonardo Da Vinci", "4d8b92684eb68a1b2c00009e")
    )

    override fun onViewCreated() {
        initGame()
    }

    fun initGame() {
        view.updateView(R.string.narrator_question, true)
        generateNewQuestion()
    }

    private fun generateNewQuestion() {
        // Pick random artist
        randomNumber = Random.nextInt(1,4)
        // Store the correct answer for future reference
        correctAnswer = artistList[randomNumber]?.get(0) ?: "Pablo Picasso"

        // Subscribe to Retrofit API response
        subscription = artworkApi
            .getArtwork(artistList[randomNumber]?.get(1) ?: "4d8b928b4eb68a1b2c0001f2", view.getContext().resources.getString(R.string.auth_token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { artwork -> parseResponseData(artwork) },
                { view.showError(R.string.error_message) }
            )
    }

    private fun parseResponseData(artwork: ArtworkModel) {
        // Update the picture downloaded
        view.updateArtwork(artwork._embedded.artworks[0]._links.thumbnail.href)

        // Mix random answers sort
        // we check which artist we picked and then try to scramble the rest of the answers
        val unknownArtist: String = view.getContext().resources.getString(R.string.unknownArtist)
        when (randomNumber) {
            1 -> { view.updateAnswers(correctAnswer, artistList[2]?.get(0) ?: unknownArtist, artistList[4]?.get(0) ?: unknownArtist, artistList[3]?.get(0) ?: unknownArtist) }
            2 -> { view.updateAnswers(artistList[4]?.get(0) ?: unknownArtist, correctAnswer, artistList[1]?.get(0) ?: unknownArtist, artistList[3]?.get(0) ?: unknownArtist) }
            3 -> { view.updateAnswers(artistList[2]?.get(0) ?: unknownArtist, artistList[4]?.get(0) ?: unknownArtist, correctAnswer, artistList[1]?.get(0) ?: unknownArtist) }
            else -> { view.updateAnswers(artistList[2]?.get(0) ?: unknownArtist, artistList[4]?.get(0) ?: unknownArtist, artistList[3]?.get(0) ?: unknownArtist, correctAnswer) }
        }

        // Reset countdown timer on new fetch
        view.resetTimer()
    }

    fun handleClickedAnswer(answer: String) {
        // User selected an answer, stop the timer
        view.stopTimer()

        // Check for the correctness of the answer
        if (answer == correctAnswer) view.updateView(R.string.narrator_correct, false)
        else view.updateView(R.string.narrator_wrong, false)

    }

    fun handleTimeOut() {
        // Timed out
        view.updateView(R.string.narrator_time_out, false)
    }

    override fun onViewDestroyed() {
        // Unsubscribe if activity gets destroyed
        subscription?.dispose()
    }
}