package com.isiweek.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    @UserUsernameUnique
    private String username;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    @UserEmailUnique
    private String email;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String password;

    @NotNull
    private Long role;

    private Long lender;

    private Long debtor;

    private Long status;

}
