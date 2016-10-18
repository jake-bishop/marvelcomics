package com.geekmode.marvelcomics.services;

import com.geekmode.marvelcomics.model.CharactersResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface CharacterService {
    @GET("v1/public/characters")
    Observable<CharactersResponse> characters(@Query("nameStartsWith") String characterName);

    @GET("/v1/public/characters/{id}")
    Observable<CharactersResponse> characterById(@Path("id") String characterId);

    @GET("/v1/public/comics/{issue}/characters")
    Observable<CharactersResponse> charactersOfComic(@Path("issue") String issueId);
}
