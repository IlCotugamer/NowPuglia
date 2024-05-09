package it.Gruppo1.NowPuglia.config.ApiConfig;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiBuilder {
    public static ResponseEntity<ApiResponse<String>> buildResponse(HttpStatus status, String message) {
        ApiResponse.ApiResponseBuilder<String> builder = ApiResponse.builder();
        builder
                .statusCode(status.value())
                .statusMessage(message)
                .returnedObjects(0)
                .totalObjects(0)
                .build();
        ApiResponse<String> risposta = builder.build();
        return ResponseEntity.status(HttpStatus.OK).body(risposta);
    }
}
