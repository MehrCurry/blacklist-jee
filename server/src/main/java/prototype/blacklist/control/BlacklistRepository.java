package prototype.blacklist.control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prototype.blacklist.entity.Blacklist;

import java.util.List;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
  List<Blacklist> findByName(String name);
}
