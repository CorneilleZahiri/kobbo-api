package com.kobbo.kobbo.dto.operation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterOperationRequest {
    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne doit pas être postérieure à celle du jour")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOperation;

    @NotBlank(message = "Le statut est vide")
    private String statut;

    @NotBlank(message = "Le type est vide")
    private String typeOperation;

    @NotBlank(message = "Le libellé est vide")
    private String libelle;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement supérieur à 0")
    private BigDecimal montant;

    @NotBlank(message = "Le mode de payement est vide")
    private String modeDePayement;

    private String referenceModeDePayement;

    @NotNull(message = "L'Id du tiers est obligatoire")
    private UUID tiersId;

    @NotNull(message = "L'Id de la nature est obligatoire")
    private Long natureId;

    @NotNull(message = "L'Id de l'utilisateur' est obligatoire")
    private UUID utilisateurId;
}
