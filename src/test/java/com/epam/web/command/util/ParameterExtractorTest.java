package com.epam.web.command.util;

import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.model.enumeration.MatchTeamNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParameterExtractorTest {
    private static final String VALID_REQUEST_HEADER = "controller?command=home-page&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final String STR_DATA = "23";
    private static final String NOT_POSITIVE_PAGE = "-1";
    private static final String DATE_STRING = "2020-10-11T10:22";

    private RequestContext requestContext;

    @Before
    public void initMethod() {
        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, VALID_REQUEST_HEADER);
    }

    @Test
    public void testExtractStringShouldExtractWhenDataExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.LOGIN, new String[]{STR_DATA});
        //when
        String str = ParameterExtractor.extractString(Parameter.LOGIN, requestContext);
        //then
        Assert.assertNotNull(str);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractStringShouldThrowExceptionWhenDataIsNotExist()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.LOGIN, null);
        //when
        ParameterExtractor.extractString(Parameter.LOGIN, requestContext);
    }

    @Test
    public void testExtractNumberShouldExtractWhenDataExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.MONEY, new String[]{STR_DATA});
        //when
        BigDecimal number = ParameterExtractor.extractNumber(Parameter.MONEY, requestContext);
        //then
        Assert.assertNotNull(number);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractNumberShouldThrowExceptionWhenDataIsNotExist() throws InvalidParametersException {
        //given
        //given
        REQUEST_PARAMETERS.put(Parameter.MONEY, null);
        //when
        ParameterExtractor.extractNumber(Parameter.MONEY, requestContext);
    }

    @Test
    public void testExtractIdShouldExtractWhenDataExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{STR_DATA});
        //when
        Long id = ParameterExtractor.extractId(requestContext);
        //then
        Assert.assertNotNull(id);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractIdShouldThrowExceptionWhenDataIsNotExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.ID, null);
        //when
        ParameterExtractor.extractId(requestContext);
    }

    @Test
    public void testExtractPageNumberShouldExtractWhenDataExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{STR_DATA});
        //when
        Integer page = ParameterExtractor.extractPageNumber(requestContext);
        //then
        Assert.assertNotNull(page);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractPageNumberShouldThrowExceptionWhenPageIsNotPositive()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.PAGE, new String[]{NOT_POSITIVE_PAGE});
        //when
        ParameterExtractor.extractPageNumber(requestContext);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractPageNumberShouldThrowExceptionWhenDataIsNotExist()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.PAGE, null);
        //when
        ParameterExtractor.extractPageNumber(requestContext);
    }

    @Test
    public void testExtractTeamBetShouldExtractWhenDataExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.BET_ON, new String[]{MatchTeamNumber.FIRST.toString()});
        //when
        MatchTeamNumber matchTeamNumber = ParameterExtractor.extractTeamBet(requestContext);
        //then
        Assert.assertNotNull(matchTeamNumber);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractTeamBetNumberShouldThrowExceptionWhenDataIsNotExist()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.BET_ON, null);
        //when
        ParameterExtractor.extractTeamBet(requestContext);
    }

    @Test
    public void testExtractDateShouldExtractWhenDataExist() throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.DATE, new String[]{DATE_STRING});
        //when
        Date date = ParameterExtractor.extractDate(requestContext);
        //then
        Assert.assertNotNull(date);
    }

    @Test(expected = InvalidParametersException.class)
    public void testExtractDateShouldThrowExceptionWhenDataIsNotExist()
            throws InvalidParametersException {
        //given
        REQUEST_PARAMETERS.put(Parameter.DATE, null);
        //when
        ParameterExtractor.extractTeamBet(requestContext);
    }

}
