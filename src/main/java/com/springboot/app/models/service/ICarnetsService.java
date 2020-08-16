package com.springboot.app.models.service;

import com.springboot.app.models.entity.Carnets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICarnetsService {

    public List<Carnets> findAll();

    public Page<Carnets> findAll(Pageable pageable);

    public void save(Carnets carnets);

    public Carnets findOne(Long id);

    public void delete(Long id);
}
