package com.springboot.app.models.service;

import com.springboot.app.models.dao.ICarnetsDao;
import com.springboot.app.models.entity.Carnets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("carnetsServiceJPA")
public class CarnetsServiceImpl implements ICarnetsService{

    @Autowired
    private ICarnetsDao carnetsDao;

    @Override
    @Transactional(readOnly = true)
    public List<Carnets> findAll() {
        return (List<Carnets>)carnetsDao.findAll();
    }

    @Override
    public Page<Carnets> findAll(Pageable pageable) {
        return carnetsDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Carnets carnets) {
        carnetsDao.save(carnets);
    }

    @Override
    @Transactional(readOnly = true)
    public Carnets findOne(Long id) {
        return carnetsDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        carnetsDao.deleteById(id);
    }
}
