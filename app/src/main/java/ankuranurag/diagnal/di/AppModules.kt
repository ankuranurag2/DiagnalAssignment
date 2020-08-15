package ankuranurag.diagnal.di

import ankuranurag.diagnal.repository.MovieRepository
import ankuranurag.diagnal.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * created by ankur on 15/8/20
 */
val appModules= module {
    viewModel {
        MainViewModel(androidApplication(),get())
    }

    single { MovieRepository() }
}