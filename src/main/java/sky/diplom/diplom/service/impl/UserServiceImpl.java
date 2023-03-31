package sky.diplom.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sky.diplom.diplom.repository.UserRepository;
import sky.diplom.diplom.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;



}
