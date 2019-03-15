package uk.ac.ncl.team15.android.retrofit;

import retrofit2.Call;
import retrofit2.http.*;
import uk.ac.ncl.team15.android.retrofit.models.ModelAuth;
import uk.ac.ncl.team15.android.retrofit.models.ModelUsers;

// Auto-generated by: http://www.jsonschema2pojo.org/
// Somewhat modified from initially generated version
public interface SaggezzaService
{
    /*
     * /fetch_token
     */
    @FormUrlEncoded
    @POST("authenticate/")
    Call<ModelAuth> fetch_token(@Field("username") String username, @Field("password") String password);

    /*
     * /users
     */
    @GET("users/")
    Call<ModelUsers> users(@Query("page") int page);

    @GET("users/")
    Call<ModelUsers> users(@Query("search") String search, @Query("department") String department, @Query("page") int page);
}
