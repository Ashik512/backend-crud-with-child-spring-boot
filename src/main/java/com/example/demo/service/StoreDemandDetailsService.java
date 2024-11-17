package com.example.demo.service;

import com.example.demo.entity.StoreDemand;
import com.example.demo.payload.request.StoreDemandDetailsDto;
import com.example.demo.payload.request.StoreDemandDto;

import java.util.List;


public interface StoreDemandDetailsService {
    void saveAll(List<StoreDemandDetailsDto> storeDemandDetailsDto, StoreDemand storeDemand);

    StoreDemand updateAll(StoreDemandDto storeDemandDto, StoreDemand storeDemand);
}
