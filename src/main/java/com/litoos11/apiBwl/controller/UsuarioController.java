package com.litoos11.apiBwl.controller;

import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litoos11.apiBwl.entity.UsuarioEntity;
import com.litoos11.apiBwl.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	@Qualifier("usuarioServiceImpl")
	private IUsuarioService usuarioService;
	
	private static final Log LOG = LogFactory.getLog(UsuarioController.class);
	
	@GetMapping("/hola")
	public String hola() {
		return "Hoal srpingboot";
	}
	
	@GetMapping("/usuarios")
	public ResponseEntity<?> getAllUsuarios(Authentication authentication){
		
		if (authentication != null) {
			LOG.info("Hola usuario, tu user name es: ".concat(authentication.getName()));
		}
		
		List<UsuarioEntity> usuarios = usuarioService.findAll();				
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		Optional<UsuarioEntity> usuarioOptional = usuarioService.findById(id);
		
		if(usuarioOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		UsuarioEntity usuario = usuarioOptional.get();
		return ResponseEntity.ok().body(usuario);
	}
	
	@PostMapping("/registro")
	public ResponseEntity<?> registrar(@Validated @RequestBody UsuarioEntity usuario, 
			BindingResult result){
		
		UsuarioEntity usuarioCreado = null;
		if(result.hasErrors()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}

		usuarioCreado = usuarioService.saveUsuario(usuario);
		
		if(usuarioCreado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
	}

}
