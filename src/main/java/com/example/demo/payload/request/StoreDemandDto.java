package com.example.demo.payload.request;

import com.example.demo.constants.DepartmentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StoreDemandDto {
    private DepartmentType departmentType;
    private String voucherNo;
    private LocalDate validTill;
    @Size(max = 8000)
    private String remarks;
    @Size(max = 100)
    private String workOrderNo;
    @Valid
    @NotEmpty
    private List<StoreDemandDetailsDto> storeDemandDetailsDtoList;

}
