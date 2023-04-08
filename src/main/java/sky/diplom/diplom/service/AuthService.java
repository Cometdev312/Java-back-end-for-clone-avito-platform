package sky.diplom.diplom.service;

import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.Role;

import javax.xml.bind.ValidationException;

public interface AuthService {

    /**
     * @param userName Login (email)
     * @param password Password
     */
    boolean login(String userName, String password);

    /**
     * @param role User role
     */
    boolean register(RegisterReqDto registerReqDto, Role role);
}