package com.springboot.app.models.dao;

import com.springboot.app.models.entity.Documento;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IDodumentoDao extends PagingAndSortingRepository<Documento, Long> {


}
