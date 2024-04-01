package com.isiweek.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginData {

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    public LoginData(String inUsername, String inEmail, String inPassword) {
        this.username = inEmail;
        this.email = inEmail;
        this.password = inPassword;
    }

    /**
     * @param inUsername the username to set
     */
    public void setUsername(String inUsername) {
        if (inUsername == null || inUsername.isBlank()) {
            return;
        }

        this.username = inUsername;
        this.email = inUsername;
    }

    /**
     * @param inEmail the email to set
     */
    public void setEmail(String inEmail) {
        if (inEmail == null || inEmail.isBlank()) {
            return;
        }

        this.username = inEmail;
        this.email = inEmail;
    }

    /**
     * @param inPassword the password to set
     */
    public void setPassword(String inPassword) {
        if (inPassword == null || inPassword.isBlank()) {
            return;
        }

        this.password = inPassword;
    }

}
