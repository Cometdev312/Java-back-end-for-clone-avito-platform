package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.diplom.diplom.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
