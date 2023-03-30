package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.diplom.diplom.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
