package com.springboot.app.controllers;

import com.springboot.app.models.entity.Carnets;
import com.springboot.app.models.service.ICarnetsService;
import com.springboot.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Secured("ROLE_ADMIN")
@Controller
@SessionAttributes("carnets")
public class CarnetsController {

    @Autowired
    @Qualifier("carnetsServiceJPA")
    private ICarnetsService carnetsService;

    /* Metodo  para listar  todos*/
    /*
    @RequestMapping(value = "/listar/carnetSanidades", method = RequestMethod.GET)
    public String listar(Model model){
        System.out.println("CDS: " + carnetsService.findAll());
        model.addAttribute("titulo", "Listado de Carnet de Sanidades");
        model.addAttribute("carnetSanidades", carnetsService.findAll());
        return "listarCarnetSanidades"; //Retornamos nombre de la vista (el nombre del archivo html)
    }
    */

    /* Metodo  para listar todos mediante una paginador */
    @RequestMapping(value = "/listar/carnetSanidades", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0")int page, Model model){
        System.out.println("CDS: " + carnetsService.findAll());

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Carnets> listaCarnets = carnetsService.findAll(pageRequest);

        PageRender<Carnets> pageRender = new PageRender<Carnets>("/listar/carnetSanidades", listaCarnets);

        model.addAttribute("titulo", "Listado de Carnet de Sanidades");
        model.addAttribute("carnetSanidades", listaCarnets);
        model.addAttribute("page", pageRender);

        return "listarCarnetSanidades"; //Retornamos nombre de la vista (el nombre del archivo html)
    }

    /* Metodo para agregar un nuevo CARNET DE SANIDAD */
    //primera fase : mostrar el formulario
    // ("/form/carnets") Hace referencia  <a th:href="@{/form/carnets}">Crear Carnet</a> de listarCarnetSanidades.html
    @RequestMapping(value = "/form/carnets")
    public String crear(Map<String, Object> model){
        Carnets carnets = new Carnets();
        model.put("carnets", carnets);
        model.put("titulo", "Formulario del Carnet de Sanidad");
        return "formCarnetSanidad"; //Retorna el nombre de la vista, tal cual como la URL del RequestMapping

    }

    /* Metodo para editar un Carnet Sanidad */
    @RequestMapping(value = "/form/carnets/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){

        Carnets carnets = null;

        if(id > 0){ //Si hay algun id a buscar
            carnets = carnetsService.findOne(id);
            if(carnets == null){
                flash.addFlashAttribute("error", "El ID del Carnet-Sanidad no existe en le BBDD!");
                return "redirect:/listar/carnetSanidades";
            }
        }else{
            flash.addFlashAttribute("error", "El ID del Carnet-Sanidad no puede ser cero!");
            return "redirect:/listar/carnetSanidades";
        }

        //Pasamos los valores a la vista
        model.put("carnets", carnets);
        model.put("titulo", "Editar un Carnet de Sanidad");

        return "formCarnetSanidad";
    }

    //Segunda fase : enviar el formulario con los datos
    // (value = "/form/carnets", method = RequestMethod.POST)  Hace referencia <form th:action="@{/form/sctrs}" th:object="${sctrs}" method="post">  de formSctrSalud.html
    @RequestMapping(value = "/form/carnets", method = RequestMethod.POST)
    public String guardar(@Valid Carnets carnets, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Formulario del Carnet de Sanidad");
            return "formCarnetSanidad";
        }
        String mensajeFlash = (carnets.getId() != null )? "Carnet-Sanidad editado con éxito!" : "Carnet-Sanidad creado con éxito!";
        carnetsService.save(carnets);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar/carnetSanidades";//Redirigimos a la vista del listar (el nombre del archivo listarCarnetSanidades.html)
    }

    @RequestMapping(value = "/eliminar/carnets/{id}")
    public  String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){

        if(id > 0){
            carnetsService.delete(id);
            flash.addFlashAttribute("success", "Carnet-Sanidad eliminado con éxito!");
        }
        return "redirect:/listar/carnetSanidades";//Redirigimos a la vista del listar (el nombre del archivo listarCarnetSanidades.html)
    }
}
