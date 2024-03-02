package com.isiweek.security;

import com.isiweek.auth.LoginData;
import com.isiweek.auth.SignupData;
import com.isiweek.user.User;
import java.util.Optional;

public interface AuthService {

    Optional<User> login(LoginData inLoginData);

    Optional<User> signup(SignupData inRegisterData);

}
