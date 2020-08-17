package com.springboot.app.controllers;

import com.springboot.app.models.entity.*;
import com.springboot.app.models.service.*;
import com.springboot.app.util.paginator.PageRender;
/*
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("colaborador")
public class ColaboradorController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    @Qualifier("colaboradorServiceJPA")
    private IColaboradorService colaboradorService;

    @Autowired
    @Qualifier("documentoServiceJPA")
    private IDocumentoService documentoService;

    @Autowired
    @Qualifier("sctrsServiceJPA")
    private ISctrsService sctrsService;

    @Autowired
    @Qualifier("sctrpServiceJPA")
    private ISctrpService sctrpService;

    @Autowired
    @Qualifier("induccionServiceJPA")
    private IInduccionService induccionService;

    @Autowired
    @Qualifier("carnetsServiceJPA")
    private ICarnetsService carnetsService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Secured("ROLE_USER")
    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/ver/colaborador/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        /* Esta llamada hace muchas consultas a las tablas de la base de datos (Clientes -> Facturas s) */
        //Cliente cliente = clienteService.findOne(id);

		/* Optimizando la consulta usando join fetch a las tablas de la base de datos
		(select c from Cliente c left join fetch c.facturas f where c.id=?1)
		###########################################################################################
		select

			cliente0_.id as id1_0_0_,
			facturas1_.id as id1_1_1_,

				cliente0_.apellido as apellido2_0_0_,
				cliente0_.create_at as create_a3_0_0_,
				cliente0_.email as email4_0_0_,
				cliente0_.foto as foto5_0_0_,
				cliente0_.nombre as nombre6_0_0_,

				facturas1_.cliente_id as cliente_5_1_1_,
				facturas1_.create_at as create_a2_1_1_,
				facturas1_.descripcion as descripc3_1_1_,
				facturas1_.observacion as observac4_1_1_,
				facturas1_.cliente_id as cliente_5_1_0__,
				facturas1_.id as id1_1_0__

			from clientes cliente0_

				left outer inner join facturas facturas1_ on cliente0_.id=facturas1_.cliente_id

			where cliente0_.id=?
		###########################################################################################
		*/
        //Colaborador colaborador = colaboradorService.fetchByIdWithFacturas(id);

        Colaborador colaborador = colaboradorService.findOne(id);

        if (colaborador == null) {
            System.out.println("1");
            flash.addFlashAttribute("error", "El empleado no existe en la base de datos!");
            System.out.println("2");
            return "redirect:/listar/colaboradores";
        }
        if(colaborador.getFoto() == null){
            colaborador.setFoto("");
        }

        model.put("colaborador", colaborador);
        model.put("titulo", "DETALLE EMPLEADO");
        return "verColaborador";
    }


    /* Metodo  para listar  todos*/
    /*
    @RequestMapping(value = "/listar/colaboradores", method = RequestMethod.GET)
    public String listar(Model model){
        model.addAttribute("titulo", "Listado de Empleados");
        model.addAttribute("colaboradores", colaboradorService.findAll());
        return "listarColaboradores"; //Retornamos nombre de la vista (el nombre del archivo html)
    }
    */

    /* Metodo  para listar todos mediante una paginador */
    @RequestMapping(value = {"/listar/colaboradores", "/"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
                         Model model,
                         @Param("keyword") String keyword,
                         Authentication authentication,
                         HttpServletRequest request) {

        if(authentication != null){
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            logger.info("Utilizando forma estática SecurityContextHandler.getContext().getAuthentication(): Usuario autenticado, username: ".concat(auth.getName()));
        }

        // 1 FORMA MANUAL - PROGRAMATICA
        if(hasRole("ROLE_ADMIN")){
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
        }else{
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }

        // 2 FORMA - SecurityContextHolderAwareRequestWrapper
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");

        if(securityContext.isUserInRole("ROLE_ADMIN")){
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso."));
        }else{
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }

        // 3 FORMA MANUA - HttpServletRequest(NATIVA)
        if(request.isUserInRole("ROLE_ADMIN")){
            logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso."));
        }else{
            logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }


        Pageable pageRequest = PageRequest.of(page, 5);

        Page<Colaborador> colaboradores = colaboradorService.findAll(pageRequest);

        if(keyword == null || keyword.length() == 0 ){
            colaboradores = colaboradorService.findAll(pageRequest);
        }else{
            List<Colaborador> colaboradoresList = colaboradorService.findAll(keyword.toUpperCase());
            colaboradores = new PageImpl<>(colaboradoresList);

        }


        //Page<Colaborador> colaboradores = colaboradorService.findAll(pageRequest);

        PageRender<Colaborador> pageRender = new PageRender<Colaborador>("/listar/colaboradores", colaboradores);

        model.addAttribute("titulo", "Listado de Empleados");
        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("page", pageRender);
        model.addAttribute("keyword", keyword);

        return "listarColaboradores"; //Retornamos nombre de la vista (el nombre del archivo html)
    }


    /* Metodo para agregar un nuevo Colaborador */
    //primera fase : mostrar el formulario
    // ("/form/colaborador") Hace referencia  <a th:href="@{/form/colaborador}">Crear Colaborador</a> de listarColaboradores.html
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form/colaborador")
    public String crear(Map<String, Object> model) {

        Colaborador colaborador = new Colaborador();

        //Listas para mostrar en los select
        List<Documento> documentos = documentoService.findAll();
        List<Sctrs> sctrsList = sctrsService.findAll();
        List<Sctrp> sctrpList = sctrpService.findAll();
        List<Induccion> inducciones = induccionService.findAll();
        List<Carnets> carnetsList = carnetsService.findAll();

        model.put("colaborador", colaborador);
        model.put("titulo", "Formulario de Empleado");

        //Mandamos las listas para mostrar en los select
        model.put("documentos", documentos);
        model.put("listaSctrs", sctrsList);
        model.put("listaSctrp", sctrpList);
        model.put("inducciones", inducciones);
        model.put("carnetsList", carnetsList);

        return "formColaborador"; //Retorna el nombre de la vista, tal cual como la URL del RequestMapping

    }

    /* Metodo para editar un Colaborador */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form/colaborador/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Colaborador colaborador = null;

        if (id > 0) { //Si hay algun id a buscar
            colaborador = colaboradorService.findOne(id);
            if (colaborador == null) {
                flash.addFlashAttribute("error", "El ID del Empleado no existe en le BBDD!");
                return "redirect:/listar/colaboradores";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del Empleado no puede ser cero!");
            return "redirect:/listar/colaboradores";
        }

        //Pasamos los valores a la vista
        //Listas para mostrar en los select
        List<Documento> documentos = documentoService.findAll();
        List<Sctrs> sctrsList = sctrsService.findAll();
        List<Sctrp> sctrpList = sctrpService.findAll();
        List<Induccion> inducciones = induccionService.findAll();
        List<Carnets> carnetsList = carnetsService.findAll();

        model.put("colaborador", colaborador);
        model.put("titulo", "Editar Empleado ");

        //Mandamos las listas para mostrar en los select
        model.put("documentos", documentos);
        model.put("listaSctrs", sctrsList);
        model.put("listaSctrp", sctrpList);
        model.put("inducciones", inducciones);
        model.put("carnetsList", carnetsList);

        System.out.println("6");
        return "formColaborador";
    }

    //Segunda fase : enviar el formulario con los datos
    // (value = "/form/colaborador", method = RequestMethod.POST)  Hace referencia <form th:action="@{/form/colaborador}" th:object="${colaborador}" method="post">  de formSctrSalud.html
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form/colaborador", method = RequestMethod.POST)
    public String guardar(@Valid Colaborador colaborador,
                          BindingResult result,
                          Model model,
                          @RequestParam("file") MultipartFile foto,
                          RedirectAttributes flash,
                          SessionStatus status) {
        System.out.println("7: " + result.getAllErrors());
        System.out.println("7.1: " + (Colaborador) model.getAttribute("colaborador"));
        if (result.hasErrors()) {
            System.out.println("8");
            model.addAttribute("titulo", "Formulario de Empleado");
            System.out.println("9");
            return "formColaborador";
        }
        if (!foto.isEmpty()) {

            if (colaborador.getId() != null
                    && colaborador.getId() > 0
                    && colaborador.getFoto() != null
                    && colaborador.getFoto().length() > 0) {
                uploadFileService.delete(colaborador.getFoto());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

            colaborador.setFoto(uniqueFilename);
            //Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
            //String rootPath = directorioRecursos.toFile().getAbsolutePath();
            //String rootPath = "C://Temp//uploads";

        }
        String mensajeFlash = (colaborador.getId() != null) ? "Empleado editado con éxito!" : "Empleado creado con éxito!";

        System.out.println("GUARDAR.fecha: " + colaborador.getFechaNacimiento());
        System.out.println("GUARDAR.edad: " + colaborador.getEdad());
        if (colaborador.getFechaNacimiento() != null){
            colaborador.calcularEdad();
        }

        colaboradorService.save(colaborador);

        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar/colaboradores";//Redirigimos a la vista del listar (el nombre del archivo listarColaboradores.html)
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/eliminar/colaborador/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Colaborador colaborador = colaboradorService.findOne(id);

            colaboradorService.delete(id);
            flash.addFlashAttribute("success", "Empleado eliminado con éxito!");

            if (uploadFileService.delete(colaborador.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + colaborador.getFoto() + " eliminada con exito!");
            }
        }
        //Redirigimos a la vista del listar (el nombre del archivo listarColaboradores.html)
        return "redirect:/listar/colaboradores";

    }

    private boolean hasRole(String role){

        SecurityContext context = SecurityContextHolder.getContext();
        if( context == null){
            return false;
        }
        Authentication auth = context.getAuthentication();

        if( auth == null){
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        /*
        // Este no trae el nombre del role
        return authorities.contains(new SimpleGrantedAuthority(role));
		*/


        for(GrantedAuthority authority: authorities){
            if (role.equals(authority.getAuthority())){
                logger.info("Hola ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
                return true;
            }
        }
        return false;

    }
}