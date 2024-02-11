package baba.loan_app.status;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StatusDTO {

    private Long id;

    @NotNull
    @StatusNameUnique
    private StatusEnum name;

}
