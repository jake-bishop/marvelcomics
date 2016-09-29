package com.geekmode.marvelcomics;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    CharacterService getCharacterService();

}
