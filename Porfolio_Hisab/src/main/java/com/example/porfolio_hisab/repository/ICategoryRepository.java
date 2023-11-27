package com.example.porfolio_hisab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.porfolio_hisab.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
