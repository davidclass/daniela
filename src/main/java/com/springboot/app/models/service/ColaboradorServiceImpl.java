package com.springboot.app.models.service;

import com.springboot.app.models.dao.IColaboradorDao;
import com.springboot.app.models.entity.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("colaboradorServiceJPA")
public class ColaboradorServiceImpl implements IColaboradorService{

    @Autowired
    private IColaboradorDao colaboradorDao;

    @Override
    @Transactional(readOnly = true)
    public List<Colaborador> findAll(String keyword) {
        if(keyword != null){
            return colaboradorDao.findAll(keyword);
        }
        return (List<Colaborador>)colaboradorDao.findAll();
    }

    @Override
    public Page<Colaborador> findAll(Pageable pageable) {
        return colaboradorDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Colaborador colaborador) {
        colaboradorDao.save(colaborador);
    }

    @Override
    @Transactional(readOnly = true)
    public Colaborador findOne(Long id) {
        return colaboradorDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        colaboradorDao.deleteById(id);
    }
}
