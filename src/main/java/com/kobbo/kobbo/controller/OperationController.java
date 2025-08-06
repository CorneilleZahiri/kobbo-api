//package com.kobbo.kobbo.controller;
//
//import com.kobbo.kobbo.dto.operation.request.RegisterOperationRequest;
//import com.kobbo.kobbo.dto.operation.response.OperationDto;
//import com.kobbo.kobbo.service.OperationService;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/operations")
//@AllArgsConstructor
//public class OperationController {
//    private final OperationService operationService;
//
//    @PostMapping
//    public ResponseEntity<OperationDto> createOperation(@Valid @RequestBody RegisterOperationRequest request,
//                                                        UriComponentsBuilder uriComponentsBuilder) {
//        OperationDto operationDto = operationService.createOperation(request);
//
//        URI location = uriComponentsBuilder.path("/operations/{id}")
//                .buildAndExpand(operationDto.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(operationDto);
//    }
//}
