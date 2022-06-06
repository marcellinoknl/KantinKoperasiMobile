package com.example.codepam.app

import com.example.codepam.model.CheckoutMakanan
import com.example.codepam.model.ResponModel
import com.example.codepam.model.TransaksiMakananMinuman
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

    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("bookingruangan")
    fun bookingruangan(
        @Field("nama") nama: String,
        @Field("prodi") prodi: String,
        @Field("nim") nim: String,
        @Field("angkatan") angkatan: String,
        @Field("namaruangan") namaruangan: String,
        @Field("jadwal") jadwal: String,
        @Field("keterangan") keterangan: String,

    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("belipulsa")
    fun belipulsa(
        @Field("nama") nama: String,
        @Field("prodi") prodi: String,
        @Field("nim") nim: String,
        @Field("angkatan") angkatan: String,
        @Field("nohp") nohp: String,
        @Field("kartu") kartu: String,

        ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
//        @Field("fcm") fcm: String
    ): Call<ResponModel>


    @POST("transaksi")
    fun transaksi(
        @Body data: TransaksiMakananMinuman
    ): Call<ResponModel>

    @GET("makananminuman")
    fun getMakananMinuman(): Call<ResponModel>

    @GET("barang")
    fun getBarang(): Call<ResponModel>

    @POST("transaksi")
    fun transaksi(
        @Body data: CheckoutMakanan
    ): Call<ResponModel>

//    @POST("chekout")
//    fun chekout(
//        @Body data: Chekout
//    ): Call<ResponModel>


    @GET("transaksi/user/{id}")
    fun getRiwayat(
        @Path("id") id: Int
    ): Call<ResponModel>

    @POST("transaksi/batal/{id}")
    fun batalChekout(
        @Path("id") id: Int
    ): Call<ResponModel>
}