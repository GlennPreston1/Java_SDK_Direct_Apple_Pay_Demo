package com.evopayments.example.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evopayments.example.webshop.data.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {

	List<OrderEntity> findTop50ByOrderByCreatedOnDesc();

}