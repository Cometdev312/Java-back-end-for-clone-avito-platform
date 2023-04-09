package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegisterReqDto;

/**
 * Service for user registration and login
 */
public interface AuthService {

    /**
     * @param userName Login (email)
     * @param password Password
     */
    boolean login(String userName, String password);

    /**
     * @param registerReqDto User req
     */
    boolean register(RegisterReqDto registerReqDto);
}
