package com.mobimeo.citynavigation.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "time")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Time {
    @Id
    @GeneratedValue()
    private Long id;
    private Long lineId;
    private Long stopId;
    private Timestamp time;

    public Time(Long lineId, Long stopId, Timestamp time) {
        this.lineId = lineId;
        this.stopId = stopId;
        this.time = time;

    }
    public  Time(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStopId() {
        return stopId;
    }

    public Date getTime() {
        return time;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public void setStopId(Long stopId) {
        this.stopId = stopId;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return Objects.equals(lineId, time.lineId) &&
                Objects.equals(stopId, time.stopId) &&
                Objects.equals(this.time, time.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lineId, stopId, time);
    }
}
