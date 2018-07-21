package com.mobimeo.citynavigation.dao;

import com.mobimeo.citynavigation.dao.model.Delay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DelayRepository extends PagingAndSortingRepository<Delay, Long> {

   Optional<Delay> findByLineName(String lineName);

}
