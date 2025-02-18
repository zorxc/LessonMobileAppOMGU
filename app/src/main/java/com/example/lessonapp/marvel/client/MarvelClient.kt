package com.example.lessonapp.marvel.client

import com.example.lessonapp.api.interfaces.MarvelService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.MessageDigest

object MarvelApiClient {
    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    const val PUBLIC_KEY = "49dc129f47c3aab78fdf5c930e9e6d64"
    const val PRIVATE_KEY = "c01fc728aaaff5b8406c134b64cd77d92e6dba90"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service: MarvelService = retrofit.create(MarvelService::class.java)
}

fun generateHash(timestamp: String, private_key: String, public_key: String): String {
    val input = timestamp + private_key + public_key
    val md = MessageDigest.getInstance("MD5")
    return md.digest(input.toByteArray()).toHex()
}

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
