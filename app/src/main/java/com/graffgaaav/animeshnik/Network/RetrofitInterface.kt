import com.graffgaaav.animeshnik.Models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface   RetrofitInterface{

      @GET("/v3/search/anime")
      fun getSearchResult(@Query("q") search: String?): Single<SearchResponse>

      @GET("/v3/top/{subtype}/1/{type}")
      fun getTopMovie(@Path("type") type : String,
                      @Path("subtype") subtype:String): Single<TopMovieResponse>

      @GET("/v3/anime/{id}/recommendations")
      fun getAnimeRecommendations(@Path("id") id : Int) : Single<RecommendationsResponse>

      @GET("/v3/anime/{Mal_id}")
      fun getAnimeDetail(@Path("Mal_id") MalId: Int): Single<AnimeDetails>

      @GET("/v3/anime/{id}/characters_staff")
      fun getAnimeCharacters(@Path("id") id : Int) : Single<CharactersResponse>

      @GET("/v3/schedule")
      fun getSchedule(): Single<ScheduleResponse>
}