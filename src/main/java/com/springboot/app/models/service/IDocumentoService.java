package com.springboot.app.models.service;

import com.springboot.app.models.entity.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDocumentoService {

    public List<Documento> findAll();

    public Page<Documento> findAll(Pageable pageable);

    public void save(Documento documento);

    public Documento findOne(Long id);

    public void delete(Long id);
}
