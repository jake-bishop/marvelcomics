package com.geekmode.marvelcomics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface CharacterService {
    @GET("/v1/public/characters")
    Call<CharactersResponse> characters(@Query("nameStartsWith") String characterName);
}
