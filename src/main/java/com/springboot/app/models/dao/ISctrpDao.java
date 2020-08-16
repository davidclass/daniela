package com.springboot.app.models.dao;

import com.springboot.app.models.entity.Sctrp;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ISctrpDao extends PagingAndSortingRepository<Sctrp, Long> {

}
