package suminjn.test0724.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import suminjn.test0724.entity.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
