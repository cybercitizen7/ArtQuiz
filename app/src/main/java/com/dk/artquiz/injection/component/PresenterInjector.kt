package com.dk.artquiz.injection.component

import com.dk.artquiz.base.BaseView
import com.dk.artquiz.injection.module.ContextModule
import com.dk.artquiz.injection.module.NetworkModule
import com.dk.artquiz.ui.artwork.ArtworkPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    fun inject(artworkPresenter: ArtworkPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}