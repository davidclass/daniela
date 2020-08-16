package com.springboot.app.models.dao;

import com.springboot.app.models.entity.Colaborador;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IColaboradorDao extends PagingAndSortingRepository<Colaborador, Long> {

    @Query("select c from Colaborador c where c.nombre like %?1%"
            + "or c.apellido like %?1% "
            + "or c.empresa like %?1%"
            + "or c.documento_id.nombre like %?1%"
            + "or c.numeroDoc like %?1%"
            + "or c.empresa like %?1%")
    public List<Colaborador> findAll(String keyword);
}
