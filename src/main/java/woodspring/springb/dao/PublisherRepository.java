package woodspring.springb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import woodspring.springb.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
