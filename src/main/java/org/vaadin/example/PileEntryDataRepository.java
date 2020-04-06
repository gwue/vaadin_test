package org.vaadin.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PileEntryDataRepository extends CrudRepository<PileEntryData, Long> {} 