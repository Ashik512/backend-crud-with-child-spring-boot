package com.example.demo.repository;

import com.example.demo.entity.StoreDemandItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDemandDetailsRepository extends JpaRepository<StoreDemandItem, Long> {

}
