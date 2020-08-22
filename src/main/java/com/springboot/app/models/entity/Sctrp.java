package com.springboot.app.models.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
/*
import javax.validation.constraints.NotEmpty;
*/
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sctrp")
public class Sctrp implements Serializable {
    /*
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    */
    @Id
    @GenericGenerator(
            name = "SEQ_SCTRP_AUTO_INCR",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "SEQ_SCTRP_AUTO_INCR"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "SEQ_SCTRP_AUTO_INCR")
    private Long id;

    @NotEmpty
    @Column(name = "codigo")
    private String codigo;

    @NotNull
    @Column(name = "emision")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date emision;

    @NotNull
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date vencimiento;

    //////////////////////////////////////////////

    @OneToMany(mappedBy = "sctrp_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Colaborador> colaboradores;


    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    // Contructor
    public Sctrp() {
        this.colaboradores = new ArrayList<>();
    }

    //////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

}
