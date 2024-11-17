package com.example.demo.controller;

import com.example.demo.common.MessageResponse;
import com.example.demo.entity.StoreDemand;
import com.example.demo.payload.request.StoreDemandDto;
import com.example.demo.payload.response.StoreDemandResponseDto;
import com.example.demo.service.StoreDemandService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/store-demands")
public class StoreDemandController {
    private static final String CREATED_SUCCESSFULLY_MESSAGE = "Created Successfully";
    private static final String UPDATED_SUCCESSFULLY_MESSAGE = "Updated Successfully";
    private static final String DELETED_SUCCESSFULLY_MESSAGE = "Deleted Successfully";

    private final StoreDemandService storeDemandService;

    public StoreDemandController(StoreDemandService storeDemandService) {
        this.storeDemandService = storeDemandService;
    }


    @Transactional
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody StoreDemandDto storeDemandDto) {
        StoreDemand storeDemand = storeDemandService.create(storeDemandDto);

        return ResponseEntity.ok(new MessageResponse(CREATED_SUCCESSFULLY_MESSAGE, storeDemand.getId()));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody StoreDemandDto storeDemandDto, @PathVariable Long id) {
        StoreDemand storeDemand = storeDemandService.update(storeDemandDto, id);

        return ResponseEntity.ok(new MessageResponse(UPDATED_SUCCESSFULLY_MESSAGE, storeDemand.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        storeDemandService.delete(id);

        return ResponseEntity.ok(new MessageResponse(DELETED_SUCCESSFULLY_MESSAGE));
    }

    @GetMapping
    public List<StoreDemandResponseDto> getAll() {

        return storeDemandService.getAll();
    }



}
