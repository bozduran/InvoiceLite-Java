package org.bozntouran.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@Log4j2
@Getter
@Setter
public class Language {

    private static Language instance;
    private ResourceBundle bundle = null;

    private Language(){
        String appConfigPath ="src/main/resources/app.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Locale locale = Locale.forLanguageTag(properties.getProperty("language"));
        initResourceBundle(locale);

    }

    public static Language getInstance() {
        if (instance == null){
            instance = new Language();
        }

        return instance;
    }

    private void initResourceBundle(Locale locale) {
        bundle = ResourceBundle.getBundle("Messages",locale);
    }

    public void updateSelectedLanguage(Locale locale){

        initResourceBundle(locale);

    }

    public String getMessage(String selectedMessage){

        String returnValue = "";
        try {
            returnValue = bundle.getString(selectedMessage);

        } catch (Exception e) {
            returnValue = "No available translation for key: " + selectedMessage;

        }

        return returnValue;
    }
}
