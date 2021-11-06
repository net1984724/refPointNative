package com.proto.demo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
public class History {
    private Integer customer_id;
    private int point;
    private String type;
    private String properties;
    private String updated_by_user;
    private Timestamp updated_datetime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getUpdatedByUser() {
        return updated_by_user;
    }

    public void setUpdatedByUser(String updated_by_user) {
        this.updated_by_user = updated_by_user;
    }

    public Timestamp getLastUpdatedAt() {
        return updated_datetime;
    }

    public void setLastUpdatedAt(Timestamp updated_datetime) {
        this.updated_datetime = updated_datetime;
    }

}
