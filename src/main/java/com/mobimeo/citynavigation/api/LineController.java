package com.mobimeo.citynavigation.api;

import com.mobimeo.citynavigation.dao.model.Line;
import com.mobimeo.citynavigation.service.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/city-navigation/v1/lines")
@Api(tags = {"lines"})
public class LineController extends AbstractRestHandler {


    @Autowired
    private LineService lineService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a line resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createLine(@RequestBody Line line,
                            HttpServletRequest request, HttpServletResponse response) {
        Line createdline = lineService.createLine(line);
        response.setHeader("Location", request.getRequestURL().append("/").append(line.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all lines for specific time and place.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Line> getAllLine(@ApiParam(value = "The page number (zero-based)", required = true)
                          @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                          @ApiParam(value = "Tha page size", required = true)
                          @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                          @ApiParam(value = "The line x coordinate", required = true)
                          @RequestParam(value = "x", required = true) Integer x,
                          @ApiParam(value = "The line y coordinate", required = true)
                          @RequestParam(value = "y", required = true) Integer y,
                          @ApiParam(value = "The timestamp", required = true)
                          @RequestParam(value = "date", required = true) String date,
                          HttpServletRequest request, HttpServletResponse response) {
        return lineService.getLinesByLocationAndTime(page, size,date,x,y);
    }
    @RequestMapping(value = "check-delay",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns boolean indicating if given line is currently delayed.")
    public
    @ResponseBody
    boolean checkLineDelation(@ApiParam(value = "The line name", required = true)
                          @RequestParam(value = "lineName", required = true) String name,

                          HttpServletRequest request, HttpServletResponse response) {

        return lineService.checkLineDelayation(name);
    }


}
