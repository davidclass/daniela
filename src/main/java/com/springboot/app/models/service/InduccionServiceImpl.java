package com.springboot.app.models.service;

import com.springboot.app.models.dao.IInduccionDao;
import com.springboot.app.models.entity.Induccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("induccionServiceJPA")
public class InduccionServiceImpl implements IInduccionService{

    @Autowired
    private IInduccionDao induccionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Induccion> findAll() {
        return (List<Induccion>)induccionDao.findAll();
    }

    @Override
    public Page<Induccion> findAll(Pageable pageable) {
        return induccionDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Induccion induccion) {
        induccionDao.save(induccion);
    }

    @Override
    @Transactional(readOnly = true)
    public Induccion findOne(Long id) {
        return induccionDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        induccionDao.deleteById(id);
    }
}
