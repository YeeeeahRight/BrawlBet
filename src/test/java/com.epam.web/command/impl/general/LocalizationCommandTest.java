package com.epam.web.command.impl.general;

import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LocalizationCommandTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String EN_LANG = "en";
    private static final String EN_LOCALE = "en_US";
    private static final String RU_LANG = "ru";
    private static final String RU_LOCALE = "ru_RU";
    private static final String NONAME_LANG = "noname";

    private RequestContext requestContext;
    private LocalizationCommand localizationCommand;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
        localizationCommand = new LocalizationCommand();
    }

    @Test
    public void testExecuteShouldReturnRedirect() throws InvalidParametersException {
        //given
        //when
        CommandResult actual = localizationCommand.execute(requestContext);
        //then
        CommandResult expected = CommandResult.redirect(VALID_REQUEST_HEADER);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExecuteShouldSetRussianLocaleWhenParameterIsRussianLang()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.LANGUAGE, new String[]{RU_LANG});
        //when
        localizationCommand.execute(requestContext);
        //then
        String actualLocale = (String)requestContext.getSessionAttribute(Attribute.LANGUAGE);
        Assert.assertEquals(RU_LOCALE, actualLocale);
    }

    @Test
    public void testExecuteShouldSetEnglishLocaleWhenParameterIsEnglishLang()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.LANGUAGE, new String[]{EN_LANG});
        //when
        localizationCommand.execute(requestContext);
        //then
        String actualLocale = (String)requestContext.getSessionAttribute(Attribute.LANGUAGE);
        Assert.assertEquals(EN_LOCALE, actualLocale);
    }

    @Test
    public void testExecuteShouldSetEnglishLocaleWhenParameterIsNotDefined()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.LANGUAGE, new String[]{NONAME_LANG});
        //when
        localizationCommand.execute(requestContext);
        //then
        String actualLocale = (String)requestContext.getSessionAttribute(Attribute.LANGUAGE);
        Assert.assertEquals(EN_LOCALE, actualLocale);
    }
}
