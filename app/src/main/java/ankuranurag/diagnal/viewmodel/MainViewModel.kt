package ankuranurag.diagnal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ankuranurag.diagnal.R
import ankuranurag.diagnal.model.MovieData
import ankuranurag.diagnal.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * created by ankur on 15/8/20
 * I am using {@link AndroidViewModel} instead of regular one,
 * as context was needed for reading the files from Assets.
 */
class MainViewModel(private val app: Application, private val repository: MovieRepository) : AndroidViewModel(app) {

    private var pageNum = 1
    private val _movieListStore = ArrayList<MovieData>()

    //Variables that are being exposed for observation
    var title = MutableLiveData<String>()
    val movieList = MutableLiveData<List<MovieData>>()

    //Load initial data
    init {
        fetchMovieList()
    }

    fun fetchMovieList() {
        if (pageNum == -1)
            return
        viewModelScope.launch {
            val response = repository.getMovieResponse(app, pageNum)
            if (response == null)
                pageNum = -1                    //-1 indicates that no more pages are left for lazy loading
            else {
                response.page?.let {
                    title.postValue(it.title ?: app.getString(R.string.app_name))
                    val list = it.movieContent?.movieList
                    if (false == list?.isNullOrEmpty()) {
                        _movieListStore.addAll(list)
                        pageNum++
                    }

                    movieList.postValue(_movieListStore)
                }
            }
        }
    }

    fun resetSearch() = movieList.postValue(_movieListStore)

    fun searchMovies(query: String) = viewModelScope.launch {
        val filterList = repository.filterMovieList(query, _movieListStore)
        movieList.postValue(filterList)
    }
}