package com.springboot.app.models.dao;

import com.springboot.app.models.entity.Induccion;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IInduccionDao extends PagingAndSortingRepository<Induccion, Long> {

}
