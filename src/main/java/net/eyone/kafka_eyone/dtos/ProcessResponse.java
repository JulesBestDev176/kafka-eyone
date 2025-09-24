package net.eyone.kafka_eyone.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessResponse<T> {
    private boolean success;
    private String message;
    private T data;
}