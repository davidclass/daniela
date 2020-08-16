package com.springboot.app.controllers;

import com.springboot.app.models.entity.Sctrp;
import com.springboot.app.models.service.ISctrpService;
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
@SessionAttributes("sctrp")
public class SctrpController {

    @Autowired
    @Qualifier("sctrpServiceJPA")
    private ISctrpService sctrpService;

    /* Metodo  para listar  todos*/
    /*
    @RequestMapping(value = "/listar/sctrPensiones", method = RequestMethod.GET)
    public String listar(Model model){
        //System.out.println(sctrpDao.findAll());
        model.addAttribute("titulo", "Listado de SCTR - Pension");
        model.addAttribute("sctrPensiones", sctrpService.findAll());
        return "listarSctrPensiones"; //Retornamos nombre de la vista (el nombre del archivo html)
    }
    */

    /* Metodo  para listar todos mediante una paginador */
    @RequestMapping(value = "/listar/sctrPensiones", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0")int page, Model model){

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Sctrp> listaSctrp = sctrpService.findAll(pageRequest);

        PageRender<Sctrp> pageRender = new PageRender<Sctrp>("/listar/sctrPensiones", listaSctrp);

        model.addAttribute("titulo", "Listado de SCTR - Pensión");
        model.addAttribute("sctrPensiones", listaSctrp);
        model.addAttribute("page", pageRender);

        return "listarSctrPensiones"; //Retornamos nombre de la vista (el nombre del archivo html)
    }

    /* Metodo para agregar un nuevo SCTRP */
    //primera fase : mostrar el formulario
    // ("/form/sctrp") Hace referencia  <a th:href="@{/form/sctrp}">Crear SCTR</a> de listarSctrPensiones.html
    @RequestMapping(value = "/form/sctrp")
    public String crear(Map<String, Object> model){
        Sctrp sctrp = new Sctrp();
        model.put("sctrp", sctrp);
        model.put("titulo", "Formulario de SCTR - Pensión");
        return "formSctrPension"; //Retorna el nombre de la vista, tal cual como la URL del RequestMapping

    }

    /* Metodo para editar un SCTRP */
    @RequestMapping(value = "/form/sctrp/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){

        Sctrp sctrp = null;

        if(id > 0){ //Si hay algun id a buscar
            sctrp = sctrpService.findOne(id);
            if(sctrp == null){
                flash.addFlashAttribute("error", "El ID del SCTR-Pensión no existe en le BBDD!");
                return "redirect:/listar/sctrPensiones";
            }
        }else{
            flash.addFlashAttribute("error", "El ID del SCTR-Pensión no puede ser cero!");
            return "redirect:/listar/sctrPensiones";
        }

        //Pasamos los valores a la vista
        model.put("sctrp", sctrp);
        model.put("titulo", "Editar SCTR - Pensión ");

        return "formSctrPension";
    }

    //Segunda fase : enviar el formulario con los datos
    // (value = "/form/sctrp", method = RequestMethod.POST)  Hace referencia <form th:action="@{/form/sctrp}" th:object="${sctrp}" method="post">  de formSctrPension.html
    @RequestMapping(value = "/form/sctrp", method = RequestMethod.POST)
    public String guardar(@Valid Sctrp sctrp, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Formulario de SCTR - Pensión");
            return "formSctrPension";
        }
        String mensajeFlash = (sctrp.getId() != null )? "SCTR-Pensión editado con éxito!" : "SCTR-Pensión creado con éxito!";
        sctrpService.save(sctrp);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar/sctrPensiones";//Redirigimos a la vista del listar (el nombre del archivo listarSctrPensiones.html)
    }

    @RequestMapping(value = "/eliminar/sctrp/{id}")
    public  String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){

        if(id > 0){
            sctrpService.delete(id);
            flash.addFlashAttribute("success", "SCTR-Pensión eliminado con éxito!");
        }
        return "redirect:/listar/sctrPensiones";//Redirigimos a la vista del listar (el nombre del archivo listarSctrPensiones.html)
    }
}
