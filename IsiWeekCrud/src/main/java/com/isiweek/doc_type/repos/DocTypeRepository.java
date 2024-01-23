package com.isiweek.doc_type.repos;

import com.isiweek.doc_type.domain.DocType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocTypeRepository extends JpaRepository<DocType, Long> {

    boolean existsByNameIgnoreCase(String name);

}
