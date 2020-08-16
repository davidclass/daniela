package com.springboot.app.models.dao;

import com.springboot.app.models.entity.Carnets;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ICarnetsDao extends PagingAndSortingRepository<Carnets, Long> {

}
