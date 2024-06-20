package com.nox.JavaBootCampAdv.repository;

import com.nox.JavaBootCampAdv.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByCompanyId(Long id);
}