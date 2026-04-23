package org.example.tpremise.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionPutDTO {

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Double montantAvant;

    @NotNull
    private Double montantApres;

    private Long remiseId;

    public TransactionPutDTO() {}
}
