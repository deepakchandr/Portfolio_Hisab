package com.example.porfolio_hisab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.porfolio_hisab.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

	@Query(value="select id from category where category_name=:name",nativeQuery=true)
	long getIdByName(@Param("name") String name);
}
