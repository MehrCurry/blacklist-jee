package prototype.blacklist.control;

import org.springframework.data.jpa.repository.JpaRepository;
import prototype.blacklist.entity.Blacklist;

/**
 * Created by guido on 05.11.14.
 */
public interface BlacklistRepository extends JpaRepository<Blacklist,Long> {
}
