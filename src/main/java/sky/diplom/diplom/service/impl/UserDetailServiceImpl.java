package sky.diplom.diplom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.repository.UserRepository;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    //данные сервис будет использоваться для проверки пользователя при аутентификации пользователя.
    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
