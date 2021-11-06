package com.proto.demo;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainService {

  @Autowired
  HistoryRepository hisRepository;

  public  String addPoint () {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    History n = new History();
    n.setCustomerId(31103110);
    n.setPoint(100);
    n.setType("Spring-JPA");
    n.setProperties("testSpringJPA");
    n.setUpdatedByUser("test_user");
    n.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
    hisRepository.save(n);
    return "Saved";
  }

  public Iterable<History> getAllHistory() {
    // This returns a JSON or XML with the users
    return hisRepository.findAll();
  }
}