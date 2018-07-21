package com.mobimeo.citynavigation.dao;

import com.mobimeo.citynavigation.dao.model.Stop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StopsRepository extends PagingAndSortingRepository<Stop, Long> {
    Page findAll(Pageable pageable);

}
