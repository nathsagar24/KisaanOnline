package com.example.kisaanonline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface KisaanOnlineAPI{

    //java.lang.String com.example.kisaanonline.LoginAndRegisterResult.getIsError()' on a null object reference
    //some problem with @Body

    @POST("authenticate")
    Call<APIToken> getToken(
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
    Call<ProductDetailsList> getProductDetails(
            @Body ProductListBody productListBody,
            @Header("Authorization") String token
    );

    @POST("services/get_product_list")
    Call<ProductDetailsList> getSearchedProducts(
            @Body SearchCredentials searchCredentials,
            @Header("Authorization") String token
    );

    @POST("services/cart/save_cartproduct_list")
    Call<CartProductSaveResult> saveCartProduct(
            @Body List<ProductCredentialsList.ProductCredentials> productCredentials,
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

    @POST("services/cart/get_cartproduct_list")
    Call<CartProducts> getCartProductList(
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

    @POST("services/cart/get_cartdetails_list")
    Call<CartDetails> getCartDetails(
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

}
