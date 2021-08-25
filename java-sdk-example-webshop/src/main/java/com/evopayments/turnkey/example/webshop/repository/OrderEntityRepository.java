package com.evopayments.turnkey.example.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evopayments.turnkey.example.webshop.data.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, String> {

	List<OrderEntity> findTop50ByOrderByCreatedOnDesc();

}