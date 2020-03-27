package org.vaadin.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<TestData, Long> {}