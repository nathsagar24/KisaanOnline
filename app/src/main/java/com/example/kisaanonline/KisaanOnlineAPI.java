package com.example.kisaanonline;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartDetailsResult;
import com.example.kisaanonline.ApiResults.CartSaveResult;
import com.example.kisaanonline.ApiResults.CartListResult;
import com.example.kisaanonline.ApiResults.LoginResult;
import com.example.kisaanonline.ApiResults.ProductListResult;
import com.example.kisaanonline.ApiResults.RegisterResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.Models.LoginCredentials;
import com.example.kisaanonline.Models.ProductCredentialsList;
import com.example.kisaanonline.Models.ProductListBody;
import com.example.kisaanonline.Models.RegistrationCredentials;
import com.example.kisaanonline.Models.SearchCredentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface KisaanOnlineAPI{

    //java.lang.String com.example.kisaanonline.LoginAndRegisterResult.getIsError()' on a null object reference
    //some problem with @Body

    @POST("authenticate")
    Call<APITokenResult> getToken(
            @Body AuthenticationCredentials authenticationCredentials
    );

    @POST("services/user/login")
    Call<LoginResult> loggedIn(
            @Body LoginCredentials loginCredentials,
            @Header("Authorization") String token
    );

    @POST("services/user/save")
    Call<RegisterResult> register(
            @Body RegistrationCredentials registrationCredentials,
            @Header("Authorization") String token
    );

    @POST("services/get_product_list")
    Call<ProductListResult> getProductDetails(
            @Body ProductListBody productListBody,
            @Header("Authorization") String token
    );

    @POST("services/get_product_list")
    Call<ProductListResult> getSearchedProducts(
            @Body SearchCredentials searchCredentials,
            @Header("Authorization") String token
    );

    @POST("services/cart/save_cartproduct_list")
    Call<CartSaveResult> saveCartProduct(
            @Body List<ProductCredentialsList.ProductCredentials> productCredentials,
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

    @POST("services/cart/get_cartproduct_list")
    Call<CartListResult> getCartList(
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

    @POST("services/cart/get_cartdetails_list")
    Call<CartDetailsResult> getCartDetails(
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

}
