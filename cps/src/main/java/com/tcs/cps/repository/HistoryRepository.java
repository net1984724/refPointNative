package com.tcs.cps.repository;

import com.tcs.cps.model.History;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<History, Integer> {
}