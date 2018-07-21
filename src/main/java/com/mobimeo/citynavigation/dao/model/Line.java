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
@Table(name = "line")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Line {
    @Id
    @GeneratedValue()
    private Long id;
    private Long lineId;
    private String lineName;

    public Line(Long lineId, String linenName) {
        this.lineId = lineId;
        this.lineName = linenName;

    }
    public Line(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(id, line.id) &&
                Objects.equals(lineId, line.lineId) &&
                Objects.equals(lineName, line.lineName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, lineId, lineName);
    }
}
