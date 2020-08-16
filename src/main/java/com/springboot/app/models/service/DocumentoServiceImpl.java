package com.springboot.app.models.service;

import com.springboot.app.models.dao.IDodumentoDao;
import com.springboot.app.models.entity.Documento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("documentoServiceJPA")
public class DocumentoServiceImpl implements IDocumentoService{

    @Autowired
    private IDodumentoDao dodumentoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Documento> findAll() {
        return (List<Documento>)dodumentoDao.findAll();
    }

    @Override
    public Page<Documento> findAll(Pageable pageable) {
        return dodumentoDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Documento documento) {
        dodumentoDao.save(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public Documento findOne(Long id) {
        return dodumentoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        dodumentoDao.deleteById(id);
    }
}
