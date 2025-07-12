package org.bozntouran.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    Language language;

    @BeforeEach
    void setUp() {
        language = Language.getInstance();
    }



    @Test
    void getMessage(){

        language.updateSelectedLanguage(Locale.forLanguageTag("el"));

        assertEquals("Βοήθεια",language.getMessage("help"));

        language.updateSelectedLanguage(Locale.forLanguageTag("en"));

        assertEquals("Help",language.getMessage("help"));

    }

    @Test
    void  getMessageFail(){
        String testKey = "test.string";
        String testValue = "No available translation for key: " + testKey;

        assertEquals(testValue,language.getMessage(testKey));

    }

}