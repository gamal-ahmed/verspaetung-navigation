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
@Table(name = "delay")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Delay {
    @Id
    @GeneratedValue()
    private Long id;
    private Long delay;
    private String lineName;

    public Delay(String lineName, Long delay) {
        this.delay = delay;
        this.lineName = lineName;

    }

    public Delay() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String linenName) {
        this.lineName = linenName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delay delay1 = (Delay) o;
        return Objects.equals(id, delay1.id) &&
                Objects.equals(delay, delay1.delay) &&
                Objects.equals(lineName, delay1.lineName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, delay, lineName);
    }
}
