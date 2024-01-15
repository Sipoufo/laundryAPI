package com.STTFV.laundryAPI.dto.responses;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DataResponse {
    private List<?> data;
    private Pageable pageable;
}
