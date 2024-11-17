package com.example.demo.service;

import com.example.demo.entity.StoreDemand;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payload.request.StoreDemandDetailsDto;
import com.example.demo.payload.request.StoreDemandDto;
import com.example.demo.payload.response.StoreDemandResponseDto;
import com.example.demo.repository.StoreDemandDetailsRepository;
import com.example.demo.repository.StoreDemandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class StoreDemandServiceImpl implements StoreDemandService {

    private final StoreDemandRepository storeDemandRepository;
    private final StoreDemandDetailsService storeDemandDetailsService;

    public StoreDemandServiceImpl(StoreDemandRepository storeDemandRepository,
                                  StoreDemandDetailsService storeDemandDetailsService,
                                  StoreDemandDetailsRepository storeDemandDetailsRepository) {
        this.storeDemandRepository = storeDemandRepository;
        this.storeDemandDetailsService = storeDemandDetailsService;
    }


    @Override
    public StoreDemand create(StoreDemandDto storeDemandDto) {
        StoreDemand storeDemand = convertToEntity(storeDemandDto);
        StoreDemand entity = storeDemandRepository.save(storeDemand);

        storeDemandDetailsService.saveAll(storeDemandDto.getStoreDemandDetailsDtoList(), entity);

        return storeDemand;
    }

    @Override
    public StoreDemand update(StoreDemandDto storeDemandDto, Long id) {
        // Step 1:Fetch the existing StoreDemand
        StoreDemand existingStoreDemand = storeDemandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store Demand", "id", id));

        updateEntity(existingStoreDemand, storeDemandDto);

        StoreDemand storeDemand = storeDemandDetailsService.updateAll(storeDemandDto, existingStoreDemand);

        return storeDemandRepository.save(storeDemand);

    }

    @Override
    public void delete(Long id) {
        // Step 1:Fetch the existing StoreDemand
        StoreDemand existingStoreDemand = storeDemandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store Demand", "id", id));

        storeDemandRepository.delete(existingStoreDemand);
    }

    @Override
    public List<StoreDemandResponseDto> getAll() {
        List<StoreDemand> demandList = storeDemandRepository.findAll();

        return convertToResponseDto(demandList);
    }

    @Override
    public StoreDemandResponseDto getSingle(Long id) {
        StoreDemand storeDemand = storeDemandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store Demand", "id", id));

        return convertToSingleResponseDto(storeDemand);

    }

    public List<StoreDemandResponseDto> convertToResponseDto(List<StoreDemand> demandList) {
        return demandList.stream().map(this::convertToSingleResponseDto).collect(Collectors.toList());
    }

    private StoreDemandResponseDto convertToSingleResponseDto(StoreDemand storeDemand) {
        // Map StoreDemand entity to StoreDemandResponseDto
        StoreDemandResponseDto responseDto = new StoreDemandResponseDto();
        responseDto.setId(storeDemand.getId());
        responseDto.setDepartmentType(storeDemand.getDepartmentType());
        responseDto.setVoucherNo(storeDemand.getVoucherNo());
        responseDto.setValidTill(storeDemand.getValidTill());
        responseDto.setWorkOrderNo(storeDemand.getWorkOrderNo());
        responseDto.setRemarks(storeDemand.getRemarks());

        // Map StoreDemandItem list to StoreDemandDetailsDto list
        List<StoreDemandDetailsDto> detailsDtoList = storeDemand.getStoreDemandItemList().stream()
                .map(item -> {
                    StoreDemandDetailsDto detailsDto = new StoreDemandDetailsDto();
                    detailsDto.setId(item.getId());
                    detailsDto.setQuantityDemanded(item.getQuantityDemanded());
                    detailsDto.setTotalIssuedQty(item.getIssuedQty());
                    detailsDto.setPriorityType(item.getPriorityType());
                    detailsDto.setIpcCmm(item.getIpcCmm());
                    return detailsDto;
                })
                .collect(Collectors.toList());

        responseDto.setStoreDemandDetailsDtoList(detailsDtoList);

        return responseDto;
    }


    private void updateEntity(StoreDemand existingStoreDemand, StoreDemandDto storeDemandDto) {
        // Step 2: Update the StoreDemand fields
        existingStoreDemand.setDepartmentType(storeDemandDto.getDepartmentType());
        existingStoreDemand.setVoucherNo(storeDemandDto.getVoucherNo());
        existingStoreDemand.setRemarks(storeDemandDto.getRemarks());
        existingStoreDemand.setWorkOrderNo(storeDemandDto.getWorkOrderNo());
    }

    public StoreDemand convertToEntity(StoreDemandDto storeDemandDto) {
        var storeDemand = new StoreDemand();

        storeDemand.setDepartmentType(storeDemandDto.getDepartmentType());
        storeDemand.setVoucherNo(storeDemandDto.getVoucherNo());
        storeDemand.setVoucherNo(storeDemandDto.getVoucherNo());
        storeDemand.setRemarks(storeDemandDto.getRemarks());
        storeDemand.setWorkOrderNo(storeDemandDto.getWorkOrderNo());
        storeDemand.setValidTill(storeDemandDto.getValidTill());

        return storeDemand;
    }

}
