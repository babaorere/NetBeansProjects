package com.isiweek.auth;

import com.isiweek.user.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignupData {

    @NotNull
    @Size(max = 255)
    @UserNameUnique
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String confirmPassword;

    @Override
    public String toString() {
        return "RegisterData [\"username\": " + username + "]";
    }

}
