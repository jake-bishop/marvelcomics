package com.geekmode.marvelcomics.services;

import com.geekmode.marvelcomics.model.CharactersResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CharacterService {
    @GET("v1/public/characters")
    Observable<CharactersResponse> characters(@Query("nameStartsWith") String characterName);
}
