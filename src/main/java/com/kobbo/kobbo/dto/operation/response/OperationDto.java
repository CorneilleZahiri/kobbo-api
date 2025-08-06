//package com.kobbo.kobbo.dto.operation.response;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.kobbo.kobbo.dto.tiers.response.TiersResponse;
//import com.kobbo.kobbo.enumeration.Statut;
//import com.kobbo.kobbo.enumeration.TypeOperation;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//public class OperationDto {
//    private UUID id;
//
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate dateOperation;
//
//    private String numeroFormater;
//
//    private Statut statut;
//
//    private TypeOperation typeOperation;
//
//    private String libelle;
//
//    private BigDecimal montant;
//
//    private String modeDePayement;
//
//    private String referenceModeDePayement;
//
//    private TiersResponse tiersResponse;
//
//    private NatureResponse natureResponse;
//
//    private UtilisateurWithLibelleRoleResponse utilisateurWithLibelleRoleResponse;
//}
