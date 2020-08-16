package com.springboot.app.models.service;

import com.springboot.app.models.entity.Sctrs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISctrsService {

    public List<Sctrs> findAll();

    public Page<Sctrs> findAll(Pageable pageable);

    public void save(Sctrs sctrs);

    public Sctrs findOne(Long id);

    public void delete(Long id);
}
