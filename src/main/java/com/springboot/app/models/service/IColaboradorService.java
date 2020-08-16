package com.springboot.app.models.service;

import com.springboot.app.models.entity.Colaborador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IColaboradorService {

    public List<Colaborador> findAll(String keyword);

    public Page<Colaborador> findAll(Pageable pageable);

    public void save(Colaborador colaborador);

    public Colaborador findOne(Long id);

    public void delete(Long id);
}
