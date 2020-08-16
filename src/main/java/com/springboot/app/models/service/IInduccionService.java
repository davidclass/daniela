package com.springboot.app.models.service;

import com.springboot.app.models.entity.Induccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IInduccionService {

    public List<Induccion> findAll();

    public Page<Induccion> findAll(Pageable pageable);

    public void save(Induccion induccion);

    public Induccion findOne(Long id);

    public void delete(Long id);
}
