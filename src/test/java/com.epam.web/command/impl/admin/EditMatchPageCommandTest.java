package com.epam.web.command.impl.admin;

import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchDtoCommandHelper;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.model.entity.dto.MatchDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class EditMatchPageCommandTest {
    private static final String REQUEST_HEADER = "controller?command=matches&page=1";
    private static final Map<String, Object> SESSION_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> REQUEST_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String[]> REQUEST_PARAMETERS = new HashMap<>();
    private static final CommandResult EXPECTED_COMMAND_RESULT = CommandResult.forward(Page.EDIT_MATCH);
    private static final String ID_PARAM = "2";
    private static final MatchDto MATCH_DTO;
    private static final List<MatchDto> MATCH_DTO_LIST;

    static {
        MATCH_DTO = new MatchDto.MatchDtoBuilder().build();
        MATCH_DTO_LIST = Collections.singletonList(MATCH_DTO);
        REQUEST_PARAMETERS.put(Parameter.ID, new String[]{ID_PARAM});
    }


    private MatchDtoCommandHelper matchDtoCommandHelper;
    private RequestContext requestContext;
    private EditMatchPageCommand editMatchPageCommand;

    @Before
    public void initMethod() {
        matchDtoCommandHelper = Mockito.mock(MatchDtoCommandHelper.class);
        MatchService matchService = Mockito.mock(MatchService.class);

        requestContext = new RequestContext(REQUEST_ATTRIBUTES,
                REQUEST_PARAMETERS, SESSION_ATTRIBUTES, REQUEST_HEADER);
        editMatchPageCommand =
                new EditMatchPageCommand(matchService, matchDtoCommandHelper);
    }

    @Test
    public void testExecuteShouldReturnForward() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchDtoCommandHelper.buildMatchDtoList(anyObject(), anyObject()))
                .thenReturn(MATCH_DTO_LIST);
        CommandResult actual = editMatchPageCommand.execute(requestContext);
        //then
        Assert.assertEquals(EXPECTED_COMMAND_RESULT, actual);
    }

    @Test
    public void testExecuteShouldAddMatchDtoAttribute() throws ServiceException,
            InvalidParametersException {
        //given
        //when
        when(matchDtoCommandHelper.buildMatchDtoList(anyObject(), anyObject()))
                .thenReturn(MATCH_DTO_LIST);
        editMatchPageCommand.execute(requestContext);
        //then
        boolean isMatchDtoAttributeExist = requestContext.getRequestAttribute(Attribute.MATCH_DTO) != null;
        Assert.assertTrue(isMatchDtoAttributeExist);
    }
}
