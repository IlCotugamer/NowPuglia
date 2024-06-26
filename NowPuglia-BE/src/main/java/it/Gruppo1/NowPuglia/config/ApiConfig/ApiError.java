package it.Gruppo1.NowPuglia.config.ApiConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    @Builder.Default
    private Date timestamp = new Date();
    private String path;
    private List<ErrorDetail> details;
}