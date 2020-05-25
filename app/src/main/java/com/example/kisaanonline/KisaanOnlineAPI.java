package com.example.kisaanonline;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface KisaanOnlineAPI{

    //java.lang.String com.example.kisaanonline.LoginAndRegisterResult.getIsError()' on a null object reference
    //some problem with @Body

    @POST("authenticate")
    Call<APIToken> getToken(
            @Body AuthenticationCredentials authenticationCredentials
    );

    @POST("services/user/login")
    Call<LoginAndRegisterResult> loggedIn(
            @Body LoginCredentials loginCredentials,
            @Header("Authorization") String token
    );

    @POST("services/user/save")
    Call<LoginAndRegisterResult> register(
            @Body RegistrationCredentials registrationCredentials,
            @Header("Authorization") String token
    );

    @POST("services/get_product_list")
    Call<ProductDetailsList> getProductDetails(
            @Body Authorizer authorizer,
            @Header("Authorization") String token
    );

}
