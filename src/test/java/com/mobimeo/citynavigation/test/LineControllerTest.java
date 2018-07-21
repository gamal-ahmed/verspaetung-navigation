package com.mobimeo.citynavigation.test;

/**
 * Uses JsonPath: http://goo.gl/nwXpb, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobimeo.citynavigation.Application;
import com.mobimeo.citynavigation.api.LineController;

import com.mobimeo.citynavigation.dao.model.Line;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class LineControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/city-navigation/v1/lines/";

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void testGetLinesByLocationAndTime() throws Exception {
        Line line1 = mockLine(0, "M4");
        //Returns the vehicles for  time 10:00:00 and coordinates x=1&y=1
        mvc.perform(get(RESOURCE_LOCATION_PATTERN + "?x=1&y=1&date=10:00:00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].lineName", is(line1.getLineName())));

    }

    @Test
    public void testCheckLineDelayation() throws Exception {

        //Returns boolean indicating if given line 200  is currently delayed
        mvc.perform(get(RESOURCE_LOCATION_PATTERN + "check-delay" + "?lineName=200")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( content().string(String.valueOf(true)));

    }

    private Line mockLine(long id, String name) {
        Line line = new Line();

        line.setId(new Long(13));
        line.setLineId(id);
        line.setLineName(name);
        return line;
    }


}
