package com.example.demo.payload.request;

import com.example.demo.constants.PriorityType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDemandDetailsDto {
    private Long id;
    @NotNull(message = "Demanded quantity must not be null!")
    @Min(value = 0)
    private Integer quantityDemanded;
    private Integer totalIssuedQty;
    private PriorityType priorityType;
    @Size(max = 8000)
    private String ipcCmm;
}
