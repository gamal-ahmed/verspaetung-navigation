package com.mobimeo.citynavigation.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@Table(name = "stop")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Stop {
    @Id
    @GeneratedValue()
    private Long id;
    private Long stopId;
    private int x;
    private int y;

    public Stop(Long stopId, int x, int y) {
        this.x = x;
        this.stopId = stopId;
        this.y = y;

    }
    public Stop(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStopId() {
        return stopId;
    }

    public void setStopId(Long stopId) {
        this.stopId = stopId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return Objects.equals(id, stop.id) &&
                Objects.equals(stopId, stop.stopId) &&
                Objects.equals(x, stop.x) &&
                Objects.equals(y, stop.y);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, stopId, x, y);
    }
}
