package com.dk.artquiz.base

import com.dk.artquiz.injection.module.ContextModule
import com.dk.artquiz.injection.module.NetworkModule
import com.dk.artquiz.injection.component.DaggerPresenterInjector
import com.dk.artquiz.injection.component.PresenterInjector
import com.dk.artquiz.ui.artwork.ArtworkPresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }


    open fun onViewCreated(){}

    open fun onViewDestroyed(){}

    private fun inject() {
        when (this) {
            is ArtworkPresenter -> injector.inject(this)
        }
    }
}