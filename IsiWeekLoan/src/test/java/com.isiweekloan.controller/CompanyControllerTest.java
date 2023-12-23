package com.isiweekloan.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.isiweekloan.controller.CompanyController;
import com.isiweekloan.controller.CustomUtils;
import com.isiweekloan.dto.CompanyDto;
import com.isiweekloan.entity.CompanyEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CompanyMapper;
import com.isiweekloan.service.CompanyService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Transactional
public class CompanyControllerTest {
    private static final String ENDPOINT_URL = "/api/company";

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(companyController)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<CompanyDto> page = new PageImpl<>(Collections.singletonList(CompanyBuilder.getDto()));

        Mockito.when(companyService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(companyService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(companyService);
    }

    @Test
    public void getById() throws Exception {
        Mockito.when(companyService.findById(ArgumentMatchers.anyCollection < PersonEntity > ()))
                .thenReturn(CompanyBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));

        Mockito.verify(companyService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(companyService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(companyService.save(ArgumentMatchers.any(CompanyDto.class)))
                .thenReturn(CompanyBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CompanyBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(companyService, Mockito.times(1)).save(ArgumentMatchers.any(CompanyDto.class));
        Mockito.verifyNoMoreInteractions(companyService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(companyService.update(ArgumentMatchers.any(), ArgumentMatchers.anyCollection < PersonEntity > ()))
                .thenReturn(CompanyBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CompanyBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(companyService, Mockito.times(1))
                .update(ArgumentMatchers.any(CompanyDto.class), ArgumentMatchers.anyCollection < PersonEntity > ());
        Mockito.verifyNoMoreInteractions(companyService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(companyService).deleteById(ArgumentMatchers.anyCollection < PersonEntity > ());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CompanyBuilder.getIds())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(companyService, Mockito.times(1))
                .deleteById(Mockito.anyCollection < PersonEntity > ());
        Mockito.verifyNoMoreInteractions(companyService);
    }
}
