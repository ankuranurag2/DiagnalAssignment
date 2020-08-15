package ankuranurag.diagnal.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ankuranurag.diagnal.App
import ankuranurag.diagnal.R
import ankuranurag.diagnal.model.MovieData
import ankuranurag.diagnal.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * created by ankur on 15/8/20
 * I am using {@link AndroidViewModel} instead of regular one,
 * as context was needed for reading the files from Assets.
 */
class MainViewModel(private val app: App, private val repository: MovieRepository) : AndroidViewModel(app) {

    private var pageNum = 1
    private val _movieList = ArrayList<MovieData>()

    //Variables that are being exposed for observation
    var title = MutableLiveData<String>()
    val movieList = MutableLiveData<ArrayList<MovieData>>()

    //Load initial data
    init {
        fetchMovieList()
    }

    fun fetchMovieList() {
        if (pageNum == -1)
            return
        viewModelScope.launch {
            val response = repository.getMovieResponse(app, pageNum)
            response?.page?.let {
                title.postValue(it.title ?: app.getString(R.string.app_name))
                val list = it.movieContent?.movieList
                if (false == list?.isNullOrEmpty()) {
                    _movieList.addAll(list)
                    movieList.postValue(_movieList)
                    pageNum++
                } else
                    pageNum = -1                    //-1 indicates that no more pages are left for lazy loading
            }
        }
    }
}