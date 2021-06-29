package io.github.mat3e.hello;

import io.github.mat3e.hello.HelloService;
import io.github.mat3e.lang.Lang;
import io.github.mat3e.lang.LangRepository;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {
    private final static String WELLCOME ="Hello"; //robimy tak bo wellcome będzie używany kilka razy
    private final static String FALLBACK_ID_WELLCOME = "Hola";
    @Test
    public void test_prepareGreeting_nullName_returnsGreetingWithFallbackName() throws Exception { //dopracowanie nazwy poprzez dodanie Name 2x
        //given

        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository); // i tutaj mamy pewność że tam gdzie jest repository w service to będzie używane nasze zamockowane repo
        // when

        var result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(WELLCOME + " " + HelloService.FALLBACK_NAME + "!!!", result);
    }
    @Test
    public void test_prepareGreeting_name_returnsGreetingWithName() throws Exception {
        //given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);
        var name= "test";
        // when
        var result = SUT.prepareGreeting(name, -1);
        //then
        assertEquals(WELLCOME + " " + name + "!!!", result);
    }
    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() throws Exception {
        //given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);
        
        // when
        var result = SUT.prepareGreeting(null, null);
        //then
        assertEquals(FALLBACK_ID_WELLCOME + " " + HelloService.FALLBACK_NAME + "!!!", result);
    }
    /*@Test // to będzie testem servletu a nie service bo teraz servlet zmienia stringa na inta
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() throws Exception {
        //given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreeting(null, "abc");
        //then
        assertEquals(FALLBACK_ID_WELLCOME + " " + HelloService.FALLBACK_NAME + "!!!", result);
    }*/

    @Test //zad
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() {
        // given
        var mockRepository = new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreeting(null, -1);

        // then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!!!", result);
    }


    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELLCOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private LangRepository alwaysReturningHelloRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELLCOME, null));
            }
        };//średnik bo to jest taka niejawna instrukcja wywołująca konstruktor ( wczesniej to nie byla jako osobna metoda)(metoda jest używana przez wszystkie testy
    }
}
