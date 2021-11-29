package com.tcs.cps.repository;

import java.util.List;

import com.tcs.cps.model.History;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Query;

@Repository
public interface HistoryRepository extends CrudRepository<History, Integer> {
    @Query("SELECT * FROM history WHERE updated_by_user = :updated_by_user")
    List<History> getHistoriesByUsername(String updated_by_user);

    @Query("select sum(point) AS points FROM history WHERE updated_by_user = :updated_by_user")
    String getSumPointsByUsername(String updated_by_user);
}