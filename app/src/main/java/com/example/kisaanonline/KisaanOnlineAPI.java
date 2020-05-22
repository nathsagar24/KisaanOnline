package com.example.kisaanonline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface KisaanOnlineAPI{

    @POST("authenticate")
    Call<APIToken> getToken(
            @Body AuthenticationCredentials authenticationCredentials
    );

}
