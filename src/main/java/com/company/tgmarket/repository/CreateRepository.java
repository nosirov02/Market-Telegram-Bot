package com.company.tgmarket.repository;

import com.company.tgmarket.entity.MebelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateRepository extends JpaRepository<MebelEntity, Integer> {
}
