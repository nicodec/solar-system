package meli.nicolas.deciancio.solar.system.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import meli.nicolas.deciancio.solar.system.dao.ForecastDao;

@Test
public class ForecastControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ForecastController target;
    @Mock
    private ForecastDao dao;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.target).build();
    }

    public void testGetForecast() throws Exception {
        when(this.dao.findByDay(anyLong())).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/forecast/weather").param("day", "25")).andExpect(status().isOk());
        verify(this.dao, times(1)).findByDay(anyLong());
    }
}