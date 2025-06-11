package com.cedacri.car_app.controller.owner;

import com.cedacri.car_app.controller.owner_request_handlers.OwnerGetAllHandler;
import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.cedacri.car_app.utils.OwnerDTOUtils.getPreparedOwnerDtoList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerGetAllHandlerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerGetAllHandler ownerGetAllHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenGetAllOwners_thenReturnsOwnerList() throws IOException {
        List<OwnerDto> responseDtoList = getPreparedOwnerDtoList();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(ownerService.getAllOwners()).thenReturn(responseDtoList);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        ownerGetAllHandler.handleRequest(request, response);

        String expectedJson = objectMapper.writeValueAsString(responseDtoList);
        String actualJson = responseContent.toString();

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
