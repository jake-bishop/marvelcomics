package com.geekmode.marvelcomics.services;

import com.geekmode.marvelcomics.model.Character;
import com.geekmode.marvelcomics.model.Comic;
import com.geekmode.marvelcomics.model.ResponseWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface CharacterService {
//    @GET("v1/public/characters")
//    Observable<CharactersResponse> characters(@Query("nameStartsWith") String characterName);

    @GET("/v1/public/characters/{id}")
    Observable<ResponseWrapper<Character>> characterById(@Path("id") String characterId);

    @GET("/v1/public/comics/{issue}/characters")
    Observable<ResponseWrapper<Character>> charactersOfComic(@Path("issue") String issueId);

    @GET("/v1/public/characters/{characterId}/comics")
    Observable<Comic> comics(@Path("characterId") String characterId, @Query("offset") int offset, @Query("limit") int limit);
}
