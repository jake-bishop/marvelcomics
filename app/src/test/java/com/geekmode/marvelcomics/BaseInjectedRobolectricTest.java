package com.geekmode.marvelcomics;

import android.content.Context;

import com.geekmode.marvelcomics.context.TestMarvelApp;
import com.geekmode.marvelcomics.injection.ApplicationComponent;
import com.geekmode.marvelcomics.injection.ApplicationModule;
import com.geekmode.marvelcomics.injection.ServiceModule;
import com.geekmode.marvelcomics.services.CharacterService;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Named;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP, application = TestMarvelApp.class)
public abstract class BaseInjectedRobolectricTest {

    @Rule
    public DaggerMockRule<ApplicationComponent> daggerMockRule = new DaggerMockRule<>(
            ApplicationComponent.class,
            new ApplicationModule(((TestMarvelApp) RuntimeEnvironment.application)),
            new TestServiceModule())
            .set(applicationComponent -> ((TestMarvelApp) RuntimeEnvironment.application).setApplicationComponent(applicationComponent));

    public class TestServiceModule extends ServiceModule {
        @Override
        public CharacterService provideCharacterService(@Named("application_context") Context context) {
            return mock(CharacterService.class);
        }
    }
}



