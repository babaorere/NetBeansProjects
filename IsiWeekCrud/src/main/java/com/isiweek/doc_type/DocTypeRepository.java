package com.isiweek.doc_type;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DocTypeRepository extends JpaRepository<DocType, Long> {

    boolean existsByNameIgnoreCase(String name);

}
