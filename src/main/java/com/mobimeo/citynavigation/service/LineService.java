package com.mobimeo.citynavigation.service;

import com.mobimeo.citynavigation.dao.DelayRepository;
import com.mobimeo.citynavigation.dao.LineRepository;
import com.mobimeo.citynavigation.dao.model.Delay;
import com.mobimeo.citynavigation.dao.model.Line;
import com.mobimeo.citynavigation.exception.NotFoundException;
import com.mobimeo.citynavigation.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class LineService {

    private static final Logger log = LoggerFactory.getLogger(LineService.class);

    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private DelayRepository delayRepository;

    public Line createLine(Line line) {
        return lineRepository.save(line);
    }

    public Optional<Line> getLine(long id) {
        return lineRepository.findById(id);
    }

    public boolean checkLineDelayation(String name) {

        Optional<Delay> optionalDelay = delayRepository.findByLineName(name);
        if (optionalDelay.isPresent()) {
            Long delayTime = optionalDelay.map(Delay::getDelay).orElseThrow(() -> new NotFoundException("no delay row for this line"));
            if (delayTime > 0)
                return true;
            else
                return false;

        }

        return false;
    }

    public Page<Line> getLinesByLocationAndTime(Integer page, Integer size, String date, int x, int y) {

        return lineRepository.findVehiclesByTimeAndCoordinates(DateUtils.converToTimeStamp(date), x, y, PageRequest.of(page, size));

    }
}
