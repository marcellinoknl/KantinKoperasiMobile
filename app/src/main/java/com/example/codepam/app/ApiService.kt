package com.example.codepam.app

import com.example.codepam.model.ResponModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.math.BigInteger

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("ktp") ktp: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nohp") nohp: String,
        @Field("email") email: String,
        @Field("password") password: String,
//        @Field("fcm") fcm: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
//        @Field("fcm") fcm: String
    ): Call<ResponModel>

    @GET("makananminuman")
    fun getMakananMinuman(): Call<ResponModel>

//    @POST("chekout")
//    fun chekout(
//        @Body data: Chekout
//    ): Call<ResponModel>

//    @FormUrlEncoded
//    @POST("cost")
//    fun ongkir(
//        @Header("key") key: String,
//        @Field("origin") origin: String,
//        @Field("destination") destination: String,
//        @Field("weight") weight: Int,
//        @Field("courier") courier: String
//    ): Call<ResponOngkir>

//    @GET("chekout/user/{id}")
//    fun getRiwayat(
//        @Path("id") id: Int
//    ): Call<ResponModel>
//
//    @POST("chekout/batal/{id}")
//    fun batalChekout(
//        @Path("id") id: Int
//    ): Call<ResponModel>
}