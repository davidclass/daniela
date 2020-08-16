package com.springboot.app.controllers;

import com.springboot.app.models.entity.Induccion;
import com.springboot.app.models.service.IInduccionService;
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
@SessionAttributes("induccion")
public class InduccionController {

    @Autowired
    @Qualifier("induccionServiceJPA")
    private IInduccionService induccionService;

    /* Metodo  para listar  todos*/
    /*
    @RequestMapping(value = "/listar/inducciones", method = RequestMethod.GET)
    public String listar(Model model){
        model.addAttribute("titulo", "Listado de Inducciones");
        model.addAttribute("inducciones", induccionService.findAll());
        return "listarInducciones"; //Retornamos nombre de la vista (el nombre del archivo html)
    }
    */

    /* Metodo  para listar todos mediante una paginador */
    @RequestMapping(value = "/listar/inducciones", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0")int page, Model model){

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Induccion> inducciones = induccionService.findAll(pageRequest);

        PageRender<Induccion> pageRender = new PageRender<Induccion>("/listar/inducciones", inducciones);

        model.addAttribute("titulo", "Listado de Inducciones");
        model.addAttribute("inducciones", inducciones);
        model.addAttribute("page", pageRender);

        return "listarInducciones"; //Retornamos nombre de la vista (el nombre del archivo html)
    }

    /* Metodo para agregar un nueva INDUCCION */
    //primera fase : mostrar el formulario
    // ("/form/induccion") Hace referencia  <a th:href="@{/form/induccion}">Crear Inducción</a> de listarInducciones.html
    @RequestMapping(value = "/form/induccion")
    public String crear(Map<String, Object> model){
        Induccion induccion = new Induccion();
        model.put("induccion", induccion);
        model.put("titulo", "Formulario de Inducción");
        return "formInduccion"; //Retorna el nombre de la vista, tal cual como la URL del RequestMapping

    }

    /* Metodo para editar una Induccion */
    @RequestMapping(value = "/form/induccion/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){

        Induccion induccion = null;

        if(id > 0){ //Si hay algun id a buscar
            induccion = induccionService.findOne(id);
            if(induccion == null){
                flash.addFlashAttribute("error", "El ID de la inducción no existe en le BBDD!");
                return "redirect:/listar/inducciones";
            }
        }else{
            flash.addFlashAttribute("error", "El ID de la inducción no puede ser cero!");
            return "redirect:/listar/inducciones";
        }

        //Pasamos los valores a la vista
        model.put("induccion", induccion);
        model.put("titulo", "Editar una Inducción ");

        return "formInduccion";
    }

    //Segunda fase : enviar el formulario con los datos
    // (value = "/form/induccion", method = RequestMethod.POST)  Hace referencia <form th:action="@{/form/induccion}" th:object="${induccion}" method="post">  de formInduccion.html
    @RequestMapping(value = "/form/induccion", method = RequestMethod.POST)
    public String guardar(@Valid Induccion induccion, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Formulario de Inducción");
            return "formInduccion";
        }
        String mensajeFlash = (induccion.getId() != null )? "Inducción editada con éxito!" : "Inducción creada con éxito!";
        induccionService.save(induccion);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar/inducciones";//Redirigimos a la vista del listar (el nombre del archivo listarInducciones.html)
    }

    @RequestMapping(value = "/eliminar/induccion/{id}")
    public  String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){

        if(id > 0){
            induccionService.delete(id);
            flash.addFlashAttribute("success", "Inducción eliminada con éxito!");
        }
        return "redirect:/listar/inducciones";//Redirigimos a la vista del listar (el nombre del archivo listarInducciones.html)
    }
}
