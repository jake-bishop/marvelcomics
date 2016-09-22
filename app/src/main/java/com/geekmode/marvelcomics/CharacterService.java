package com.geekmode.marvelcomics;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface CharacterService {
    @GET("v1/public/characters")
    Observable<CharactersResponse> characters(@Query("nameStartsWith") String characterName);
}
