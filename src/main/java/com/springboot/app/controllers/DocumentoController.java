package com.springboot.app.controllers;

import com.springboot.app.models.entity.Documento;
import com.springboot.app.models.service.IDocumentoService;
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
@SessionAttributes("documento")
public class DocumentoController {

    @Autowired
    @Qualifier("documentoServiceJPA")
    private IDocumentoService documentoService;
    /* Metodo  para listar  todos*/
    /*
    @RequestMapping(value = "/listar/documentos", method = RequestMethod.GET)
    public String listar(Model model){
        model.addAttribute("titulo", "Listado de Documentos");
        model.addAttribute("documentos", documentoService.findAll());
        return "listarDocumentos"; //Retornamos nombre de la vista (el nombre del archivo html)
    }
    */

    /* Metodo  para listar todos mediante una paginador */
    @RequestMapping(value = "/listar/documentos", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0")int page, Model model){

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Documento> documentos = documentoService.findAll(pageRequest);

        PageRender<Documento> pageRender = new PageRender<Documento>("/listar/documentos", documentos);

        model.addAttribute("titulo", "Listado de Tipos de Documentos");
        model.addAttribute("documentos", documentos);
        model.addAttribute("page", pageRender);

        return "listarDocumentos"; //Retornamos nombre de la vista (el nombre del archivo html)
    }

    /* Metodo para agregar un nuevo Documento */
    //primera fase : mostrar el formulario
    // ("/form/documento") Hace referencia  <a th:href="@{/form/documento}">Crear SCTR</a> de listarDocumentos.html
    @RequestMapping(value = "/form/documento")
    public String crear(Map<String, Object> model){
        Documento documento = new Documento();
        model.put("documento", documento);
        model.put("titulo", "Formulario de Documento");
        return "formDocumento"; //Retorna el nombre de la vista, tal cual como la URL del RequestMapping

    }

    /* Metodo para editar un Documento */
    @RequestMapping(value = "/form/documento/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){

        Documento documento = null;

        if(id > 0){ //Si hay algun id a buscar
            documento = documentoService.findOne(id);
            if(documento == null){
                flash.addFlashAttribute("error", "El ID del Documento no existe en le BBDD!");
                return "redirect:/listar/documentos";
            }
        }else{
            flash.addFlashAttribute("error", "El ID del Documento no puede ser cero!");
            return "redirect:/listar/documentos";
        }

        //Pasamos los valores a la vista
        model.put("documento", documento);
        model.put("titulo", "Editar un Documento ");

        return "formDocumento";
    }

    //Segunda fase : enviar el formulario con los datos
    // (value = "/form/documento", method = RequestMethod.POST)  Hace referencia <form th:action="@{/form/documento}" th:object="${documento}" method="post">  de formDocumento.html
    @RequestMapping(value = "/form/documento", method = RequestMethod.POST)
    public String guardar(@Valid Documento documento, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo", "Formulario de Documento");
            return "formDocumento";
        }
        String mensajeFlash = (documento.getId() != null )? "Documento editado con éxito!" : "Documento creado con éxito!";
        documentoService.save(documento);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar/documentos";//Redirigimos a la vista del listar (el nombre del archivo listarDocumentos.html)
    }

    @RequestMapping(value = "/eliminar/documento/{id}")
    public  String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){

        if(id > 0){
            documentoService.delete(id);
            flash.addFlashAttribute("success", "Documento eliminado con éxito!");
        }
        return "redirect:/listar/documentos";//Redirigimos a la vista del listar (el nombre del archivo listarDocumentos.html)
    }
}
