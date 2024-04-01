package com.isiweek.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SignupData {

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String confirmPassword;

    private Long idRole;

    private Long idLender;

    private Long idDebtor;

    private Long idStatus;

    public SignupData() {
        this("", "", "", "", 0L, 0L, 0L, 0L);
    }

    public SignupData(String inUsername, String inEmail, String inPassword, String inConfirmPassword, Long inIdRole, Long inIdLender, Long inIdDebtor, Long inIdStatus) {
        this.username = (inEmail == null) ? "" : inEmail;
        this.email = (inEmail == null) ? "" : inEmail;
        this.password = (inPassword == null) ? "" : inPassword;
        this.confirmPassword = (inConfirmPassword == null) ? "" : inConfirmPassword;
        this.idRole = inIdRole == null ? 0 : inIdRole;
        this.idLender = inIdLender == null ? 0 : inIdLender;
        this.idDebtor = inIdDebtor == null ? 0 : inIdDebtor;
        this.idStatus = inIdStatus == null ? 0 : inIdStatus;
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

    /**
     * @param inConfirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String inConfirmPassword) {
        if (inConfirmPassword == null || inConfirmPassword.isBlank()) {
            return;
        }

        this.confirmPassword = inConfirmPassword;
    }

    /**
     * @param inIdRole the idRole to set
     */
    public void setIdRole(Long inIdRole) {
        if (inIdRole == null || inIdRole <= 0) {
            return;
        }

        this.idRole = inIdRole;
    }

    /**
     * @param inIdLender the idLender to set
     */
    public void setIdLender(Long inIdLender) {
        if (inIdLender == null || inIdLender <= 0) {
            return;
        }

        this.idLender = inIdLender;
    }

    /**
     * @param inIdDebtor the idDebtor to set
     */
    public void setIdDebtor(Long inIdDebtor) {
        if (inIdDebtor == null || inIdDebtor <= 0) {
            return;
        }

        this.idDebtor = inIdDebtor;
    }

    /**
     * @param inIdStatus the idStatus to set
     */
    public void setIdStatus(Long inIdStatus) {
        if (inIdStatus == null || inIdStatus <= 0) {
            return;
        }

        this.idStatus = inIdStatus;
    }

    public void reset() {
        this.username = "";
        this.email = "";
        this.password = "";
        this.confirmPassword = "";
        this.idRole = 0L;
        this.idLender = 0L;
        this.idDebtor = 0L;
        this.idStatus = 0L;
    }

}
