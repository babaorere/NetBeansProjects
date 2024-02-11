package baba.loan_app.status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusRepository extends JpaRepository<Status, Long> {

    boolean existsByName(StatusEnum name);

}
