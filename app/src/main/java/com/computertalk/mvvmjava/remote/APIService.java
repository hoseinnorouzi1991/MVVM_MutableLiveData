package com.computertalk.mvvmjava.remote;

import com.computertalk.mvvmjava.remote.user.UsersList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

   @GET("/StoreCode/user.php")
    Call<UsersList> getUsersList(
            @Query("name") String name
   );

   @FormUrlEncoded
    @POST("/StoreCode/user.php")
    Call<UsersList> getUsersListByPostRequest(
            @Field("name") String name
   );
}
