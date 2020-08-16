package ankuranurag.diagnal.repository

import android.content.Context
import ankuranurag.diagnal.model.MovieData
import ankuranurag.diagnal.model.MovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * created by ankur on 15/8/20
 */
class MovieRepository {

    suspend fun getMovieResponse(context: Context, pageNum: Int = 1): MovieResponse? {
        try {
            val jsonString = withContext(Dispatchers.IO) {
                context.assets.open("page${pageNum}.json")
                    .bufferedReader()
                    .use { it.readText() }
            }

            if (jsonString.isBlank())
                return null
            val typeToken = object : TypeToken<MovieResponse>() {}.type
            return Gson().fromJson(jsonString, typeToken)
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun filterMovieList(query: String, movieList: List<MovieData>) =
        movieList.filter { it.name!!.toLowerCase().contains(query.toLowerCase()) }

}