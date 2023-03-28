package sky.diplom.diplom.service;

import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReqDto registerReqDto, Role role);
}