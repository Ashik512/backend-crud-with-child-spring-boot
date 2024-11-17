package com.example.demo.service;

import com.example.demo.entity.StoreDemand;
import com.example.demo.payload.request.StoreDemandDto;
import com.example.demo.payload.response.StoreDemandResponseDto;

import java.util.List;


public interface StoreDemandService {
    StoreDemand create(StoreDemandDto storeDemandDto);

    StoreDemand update(StoreDemandDto storeDemandDto, Long id);

    void delete(Long id);

    List<StoreDemandResponseDto> getAll();
}
