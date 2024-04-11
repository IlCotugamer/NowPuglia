package it.Gruppo1.NowPuglia.config.ApiConfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private int statusCode;
    private String statusMessage;
    private int returnedObjects;
    private int totalObjects;
    private T payload;

    @JsonInclude(Include.NON_NULL)
    private ApiError errors;
}