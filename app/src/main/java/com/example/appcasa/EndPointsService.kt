package com.example.appcasa

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface EndPointsService {

    @POST ("/api/register")
    fun addUser(@Body newUser: User): Call<RegistoResponse>

    @FormUrlEncoded
    @POST("/api/authenticate")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("/checkToken")
    fun checkToken(
            @Header("Cookie" ) token: String
    ): Call<ResponseBody>

    @GET("/api/item")
    fun getItem(): Call<List<Item>>

    @GET("/api/item/{id}")
    fun getItem(@Path("id") id: String): Call<List<Item>>

    @FormUrlEncoded
    @POST("/api/item")
    fun addItem(
        @Field("name") name: String,
        @Field("numero") numeroRefeicoes: Int?,
        @Field("stock") stock: String,
        @Field("notas") notas: String,
        @Field("kit_details") kitDetails: String,
        @Field("check_in") checkIn: String,
        @Field("check_out") checkOut: String,
        @Field("appetizer") appetizer: String,
        @Field("side_dish") sideDish: String,
        @Field("dessert") dessert: String,
        @Field("gaps") gaps: Int?,
        @Field("spoons") spoons: Int?,
        @Field("napkin") napkin: Int?,
        @Field("cuvettes") cuvettes: Int?,
        @Field("cover") cover: Int?,
        @Field("kitchen_paper_rolls") kitchenPaperRolls: Int?,
        @Field("rolls_toilet_paper") rollsToiletPaper: Int?,
        @Field("mistolim") mistolim: Int?,
        @Field("dishwashing_detergent") dishwashingDetergent: Int?,
        @Field("floor_detergent") floorDetergent: Int?,
        @Field("gloves") gloves: Int?,
        @Field("masks") masks: Int?

    ): Call<AddItemResponse>

    @FormUrlEncoded
    @POST("/api/item/{id}")
    fun updateItem(
            @Path("id") id: String,
            @Field("name") name: String,
            @Field("numero") numeroRefeicoes: Int?,
            @Field("stock") stock: String,
            @Field("notas") notas: String,
            @Field("kit_details") kitDetails: String,
            @Field("check_in") checkIn: String,
            @Field("check_out") checkOut: String,
            @Field("appetizer") appetizer: String,
            @Field("side_dish") sideDish: String,
            @Field("dessert") dessert: String,
            @Field("gaps") gaps: Int?,
            @Field("spoons") spoons: Int?,
            @Field("napkin") napkin: Int?,
            @Field("cuvettes") cuvettes: Int?,
            @Field("cover") cover: Int?,
            @Field("kitchen_paper_rolls") kitchenPaperRolls: Int?,
            @Field("rolls_toilet_paper") rollsToiletPaper: Int?,
            @Field("mistolim") mistolim: Int?,
            @Field("dishwashing_detergent") dishwashingDetergent: Int?,
            @Field("floor_detergent") floorDetergent: Int?,
            @Field("gloves") gloves: Int?,
            @Field("masks") masks: Int?

    ): Call<AddItemResponse>

    @DELETE("/api/item/{id}")
    fun deleteItem(@Path("id") id: String): Call<Unit>

    @GET("/api/user/{email}")
    fun getProfile(@Path("email") email: String): Call<List<ProfileInfo>>

    @FormUrlEncoded
    @PUT("/api/updatePassword")
    fun changePassword(
        @Field("email") email: String,
        @Field("password") password: String,
    ):Call<ChangePasswordResponse>

}