package com.example.demo.repository;

import com.example.demo.entity.StoreDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDemandRepository extends JpaRepository<StoreDemand, Long> {

}
