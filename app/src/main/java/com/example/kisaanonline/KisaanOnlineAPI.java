package com.example.kisaanonline;

import com.example.kisaanonline.ApiResults.APITokenResult;
import com.example.kisaanonline.ApiResults.CartDetailsResult;
import com.example.kisaanonline.ApiResults.CartSaveResult;
import com.example.kisaanonline.ApiResults.CartListResult;
import com.example.kisaanonline.ApiResults.CheckoutResult;
import com.example.kisaanonline.ApiResults.CityListResult;
import com.example.kisaanonline.ApiResults.RemoveFromCartResult;
import com.example.kisaanonline.ApiResults.LoginResult;
import com.example.kisaanonline.ApiResults.MaxPriceResult;
import com.example.kisaanonline.ApiResults.PincodeListResult;
import com.example.kisaanonline.ApiResults.ProductListResult;
import com.example.kisaanonline.ApiResults.RegisterResult;
import com.example.kisaanonline.ApiResults.StateListResult;
import com.example.kisaanonline.Models.AuthenticationCredentials;
import com.example.kisaanonline.Models.CityCredentials;
import com.example.kisaanonline.Models.LoginCredentials;
import com.example.kisaanonline.Models.PincodeCredentials;
import com.example.kisaanonline.Models.ProductCredentials;
import com.example.kisaanonline.Models.RegistrationCredentials;
import com.example.kisaanonline.Models.SearchCredentials;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
    Call<ProductListResult> getProductList(
            @Body SearchCredentials searchCredentials,
            @Header("Authorization") String token
    );

    @POST("services/get_product_list")
    Call<ProductListResult> getSearchedProducts(
            @Body SearchCredentials searchCredentials,
            @Header("Authorization") String token
    );

    @POST("services/cart/save_cartproduct_list")
    Call<CartSaveResult> saveCartProduct(
            @Body List<ProductCredentials> productCredentials,
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

    @POST("services/get_max_price")
    Call<MaxPriceResult> getMaxPrice(
            @Header("Authorization") String token
    );

    @POST("services/get_state_list")
    Call<StateListResult> getStateList(
            @Header("Authorization") String token
    );

    @POST("services/get_city_list")
    Call<CityListResult> getCityList(
            @Body CityCredentials cityCredentials,
            @Header("Authorization") String token
    );

    @POST("services/get_pincode_list")
    Call<PincodeListResult> getPincodeList(
            @Body PincodeCredentials pincodeCredentials,
            @Header("Authorization") String token
    );

    @Multipart
    @POST("services/checkout/save")
    Call<CheckoutResult> uploadPaymentDetails(
            @Header("Authorization") String token,
            @Header("user-id") String userId,
            @Part MultipartBody.Part userInfo,
            @Part MultipartBody.Part paymentPic
    );

    @POST("services/cart/delete_from_cart/{cart_id}")
    Call<RemoveFromCartResult> removeFromCart(
            @Path("cart_id") String cartId,
            @Header("Authorization") String token,
            @Header("user-id") String userId
    );

}
