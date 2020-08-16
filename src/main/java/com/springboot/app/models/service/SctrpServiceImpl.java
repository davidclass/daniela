package com.springboot.app.models.service;

import com.springboot.app.models.dao.ISctrpDao;
import com.springboot.app.models.entity.Sctrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sctrpServiceJPA")
public class SctrpServiceImpl implements ISctrpService{

    @Autowired
    private ISctrpDao sctrpDao;

    @Override
    @Transactional(readOnly = true)
    public List<Sctrp> findAll() {
        return (List<Sctrp>)sctrpDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sctrp> findAll(Pageable pageable) {
        return sctrpDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Sctrp sctrp) {
        sctrpDao.save(sctrp);
    }

    @Override
    @Transactional(readOnly = true)
    public Sctrp findOne(Long id) {
        return sctrpDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sctrpDao.deleteById(id);
    }
}
