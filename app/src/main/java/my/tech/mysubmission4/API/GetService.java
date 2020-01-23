package my.tech.mysubmission4.API;

import my.tech.mysubmission4.entity.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetService {

    @GET("3/discover/movie")
    Call<MovieResponse> getAllMovies(@Query("api_key") String apiKey,
                                     @Query("language") String language);

    @GET("3/discover/tv")
    Call<MovieResponse> getAllShow(@Query("api_key") String apiKey,
                                   @Query("language") String language);

}
