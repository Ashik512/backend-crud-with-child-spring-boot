package com.example.demo.service;

import com.example.demo.entity.StoreDemand;
import com.example.demo.entity.StoreDemandItem;
import com.example.demo.payload.request.StoreDemandDetailsDto;
import com.example.demo.payload.request.StoreDemandDto;
import com.example.demo.repository.StoreDemandDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StoreDemandDetailsServiceImpl implements StoreDemandDetailsService {

    private final StoreDemandDetailsRepository storeDemandDetailsRepository;

    public StoreDemandDetailsServiceImpl(StoreDemandDetailsRepository storeDemandDetailsRepository) {
        this.storeDemandDetailsRepository = storeDemandDetailsRepository;
    }

    @Override
    public void saveAll(List<StoreDemandDetailsDto> storeDemandDetailsDtoList, StoreDemand storeDemand) {
         List<StoreDemandItem> storeDemandItems = new ArrayList<>();
         storeDemandDetailsDtoList.forEach(storeDemandDetailsDto -> prepareEntity(storeDemandDetailsDto, new StoreDemandItem(), storeDemand, storeDemandItems));

        storeDemandDetailsRepository.saveAll(storeDemandItems);

    }

    @Override
    public StoreDemand updateAll(StoreDemandDto storeDemandDto, StoreDemand existingStoreDemand) {
        // Step 3: Retrieve existing items and prepare lists for updated and deleted items
        List<StoreDemandItem> existingItems = existingStoreDemand.getStoreDemandItemList();
        List<StoreDemandItem> updatedItems = new ArrayList<>();
        List<StoreDemandItem> itemsToDelete = new ArrayList<>();

        // Map DTO list by ID for easy lookup of existing items (only DTOs with IDs)
        Map<Long, StoreDemandDetailsDto> dtoMap = storeDemandDto.getStoreDemandDetailsDtoList()
                .stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(StoreDemandDetailsDto::getId, Function.identity()));

        // Map existing items by ID for quick access
        Map<Long, StoreDemandItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(StoreDemandItem::getId, Function.identity()));

        // Step 4: Process items in DTO
        for (StoreDemandDetailsDto dto : storeDemandDto.getStoreDemandDetailsDtoList()) {
            if (dto.getId() != null && existingItemsMap.containsKey(dto.getId())) {
                // Update existing item
                StoreDemandItem item = existingItemsMap.get(dto.getId());

                item.setQuantityDemanded(dto.getQuantityDemanded());
                item.setIssuedQty(dto.getTotalIssuedQty());
                item.setPriorityType(dto.getPriorityType());
                item.setIpcCmm(dto.getIpcCmm());
                item.setIsActive(true);
                updatedItems.add(item);
            } else if (dto.getId() == null) {
                // Add new item
                StoreDemandItem newItem = new StoreDemandItem();

                newItem.setQuantityDemanded(dto.getQuantityDemanded());
                newItem.setIssuedQty(dto.getTotalIssuedQty());
                newItem.setPriorityType(dto.getPriorityType());
                newItem.setIpcCmm(dto.getIpcCmm());
                newItem.setIsActive(true);
                newItem.setStoreDemand(existingStoreDemand); // Set parent reference if needed
                updatedItems.add(newItem);
            }
        }

        // Step 5: Identify items for deletion
        for (StoreDemandItem item : existingItems) {
            if (!dtoMap.containsKey(item.getId())) {
                itemsToDelete.add(item);
            }
        }

        // Perform batch delete if there are items to delete
        if (!itemsToDelete.isEmpty()) {
            storeDemandDetailsRepository.deleteAll(itemsToDelete);
        }

        // Step 6: Update the StoreDemand's item list and save
        existingStoreDemand.setStoreDemandItemList(updatedItems);

        return existingStoreDemand;
    }

    private void prepareEntity(StoreDemandDetailsDto storeDemandDetailsDto, StoreDemandItem storeDemandItem, StoreDemand storeDemand, List<StoreDemandItem> storeDemandItems) {
        storeDemandItem.setQuantityDemanded(storeDemandDetailsDto.getQuantityDemanded());
        storeDemandItem.setIssuedQty(storeDemandDetailsDto.getTotalIssuedQty());
        storeDemandItem.setPriorityType(storeDemandDetailsDto.getPriorityType());
        storeDemandItem.setIpcCmm(storeDemandDetailsDto.getIpcCmm());
        storeDemandItem.setStoreDemand((storeDemand));

        storeDemandItems.add(storeDemandItem);
    }
}
