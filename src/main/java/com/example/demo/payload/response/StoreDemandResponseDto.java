package com.example.demo.payload.response;

import com.example.demo.constants.DepartmentType;
import com.example.demo.payload.request.StoreDemandDetailsDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDemandResponseDto {
    private Long id;
    private DepartmentType departmentType;
    private String voucherNo;
    private LocalDate validTill;
    private String workOrderNo;
    private String remarks;
    private List<StoreDemandDetailsDto> storeDemandDetailsDtoList;
}
