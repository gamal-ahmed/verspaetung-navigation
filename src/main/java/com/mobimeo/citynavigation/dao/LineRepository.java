package com.mobimeo.citynavigation.dao;

import com.mobimeo.citynavigation.dao.model.Line;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

public interface LineRepository extends JpaRepository<Line, Long> {

    @Query(value = "SELECT l.* FROM line l JOIN time t on  l.line_id=t.line_id " +
            "where stop_id=(select stop_id from stop where x=?2 and y=?3 ) " +
            "and time =?1 ORDER BY l.line_id",
            countQuery = "SELECT l.* FROM line l JOIN time t on  l.line_id=t.line_id \" +\n" +
                    "\"where stop_id=(select stop_id from stop where x=?1 and y=?2 ) \" +\n" +
                    "\"and time =?1  ", nativeQuery = true)
    Page<Line> findVehiclesByTimeAndCoordinates(Timestamp date, int x, int y, Pageable pageable);


}
