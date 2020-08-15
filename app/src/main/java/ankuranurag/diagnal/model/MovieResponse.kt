package ankuranurag.diagnal.model

import com.google.gson.annotations.SerializedName


/**
 * created by ankur on 15/8/20
 */
data class MovieResponse(
    @SerializedName("page")
    val page: Page?
)

data class Page(
    @SerializedName("title")
    val title: String?,
    @SerializedName("total-content-items")
    val totalContentItems: String?,
    @SerializedName("page-num")
    val pageNum: String?,
    @SerializedName("page-size")
    val pageSize: String?,
    @SerializedName("content-items")
    val movieList: MovieList?
)

data class MovieList(
    @SerializedName("content")
    val content: List<MovieData>?
)

data class MovieData(
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster-image")
    val posterImage: String?
)