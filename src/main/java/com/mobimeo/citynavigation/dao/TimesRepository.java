package com.mobimeo.citynavigation.dao;

import com.mobimeo.citynavigation.dao.model.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TimesRepository extends PagingAndSortingRepository<Time, Long> {
    Page findAll(Pageable pageable);

}
