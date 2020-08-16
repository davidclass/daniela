package com.springboot.app.models.service;

import com.springboot.app.models.entity.Sctrp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISctrpService {

    public List<Sctrp> findAll();

    public Page<Sctrp> findAll(Pageable pageable);

    public void save(Sctrp sctrp);

    public Sctrp findOne(Long id);

    public void delete(Long id);
}
