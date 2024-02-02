package com.example.codestates.bgcolor.repository;

import com.example.codestates.bgcolor.entity.BgColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BgColorRepository extends JpaRepository<BgColor,Long> {
    Optional<BgColor> findBgColorByColorName(String name);
}
