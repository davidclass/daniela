package com.springboot.app.models.service;

import com.springboot.app.models.dao.ISctrsDao;
import com.springboot.app.models.entity.Sctrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sctrsServiceJPA")
public class SctrsServiceImpl implements ISctrsService{

    @Autowired
    private ISctrsDao sctrsDao;

    @Override
    @Transactional(readOnly = true)
    public List<Sctrs> findAll() {
        return (List<Sctrs>)sctrsDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sctrs> findAll(Pageable pageable) {
        return sctrsDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Sctrs sctrs) {
        sctrsDao.save(sctrs);
    }

    @Override
    @Transactional(readOnly = true)
    public Sctrs findOne(Long id) {
        return sctrsDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sctrsDao.deleteById(id);
    }
}
