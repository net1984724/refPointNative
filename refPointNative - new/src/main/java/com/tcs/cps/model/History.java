package com.tcs.cps.model;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("history")
public class History {
    private Integer customer_id;
    private int point;
    private String type;
    private String properties;
    private String updated_by_user;
    private Timestamp updated_datetime;

    @Id
    private Integer id;

}
