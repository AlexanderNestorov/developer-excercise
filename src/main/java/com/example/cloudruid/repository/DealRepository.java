package com.example.cloudruid.repository;

import com.example.cloudruid.model.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<Deal> findDealById(Long id);

    Optional<Deal> findDealByName(String name);
}
