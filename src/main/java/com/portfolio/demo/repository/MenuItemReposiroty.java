package com.portfolio.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.demo.entity.MenuItem;

@Repository
public interface MenuItemReposiroty extends JpaRepository<MenuItem, Long> {

}
