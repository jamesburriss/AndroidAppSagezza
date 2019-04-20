package uk.ac.ncl.team15.android.retrofit;

import retrofit2.Call;
import retrofit2.http.*;
import uk.ac.ncl.team15.android.retrofit.models.ModelAuth;
import uk.ac.ncl.team15.android.retrofit.models.ModelJob;
import uk.ac.ncl.team15.android.retrofit.models.ModelJobs;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
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
    @GET("users/{id}")
    Call<ModelUser> users(@Path("id") int id);

    @GET("users/")
    Call<ModelUsers> users(@Query("search") String search, @Query("department") String department, @Query("page") int page);

    /*
     * /jobs
     */
    @GET("jobs/{id}")
    Call<ModelJob> jobs(@Path("id") int id);

    @GET("jobs/")
    Call<ModelJobs> jobs(@Query("search") String search, @Query("location") String location, @Query("hours") String hours, @Query("skill_level") String skill_level, @Query("page") int page);
}