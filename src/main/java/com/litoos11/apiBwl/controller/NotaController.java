package com.litoos11.apiBwl.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litoos11.apiBwl.component.Constantes.TIPO_MSG;
import com.litoos11.apiBwl.entity.NotaEntity;
import com.litoos11.apiBwl.entity.UsuarioEntity;
import com.litoos11.apiBwl.model.MensajeResponseModel;
import com.litoos11.apiBwl.service.INotaService;
import com.litoos11.apiBwl.service.IUsuarioService;

@RestController
@RequestMapping("/nota")
public class NotaController {

	@Autowired
	@Qualifier("notaServiceImpl")
	private INotaService notaService;
	
	@Autowired
	@Qualifier("usuarioServiceImpl")
	private IUsuarioService usuarioService;
	
	private static final Log LOG = LogFactory.getLog(NotaController.class);
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotaById(@PathVariable Long id, Authorization auth) {
		LOG.info("ID: " + id);
		Optional<NotaEntity> notaOptional = notaService.findById(id);
		
		if(notaOptional.isEmpty()) {
			LOG.info("Authorization: " + auth);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	
		NotaEntity nota = notaOptional.get();
		return ResponseEntity.ok().body(nota);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> getNotasByUsuarioId(@PathVariable Long usuarioId, Authentication authentication){
		
		if(authentication != null) {
			LOG.info("Authentication: " + authentication);
		}
		
		List<NotaEntity> notas = notaService.findAllByUsuarioId(usuarioId);
		
		if(notas.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.ok().body(notas);
		
	}
	
	@PostMapping("/")	
	public ResponseEntity<?> createNota(@Valid @RequestBody NotaEntity nota, BindingResult result, Authentication authentication){
		
		LOG.info("Usuario: " + authentication.getName());		
		UsuarioEntity usuarioLogueado = usuarioService.findByCorreo(authentication.getName());
		
		MensajeResponseModel msg = new MensajeResponseModel();
		
		if(usuarioLogueado == null) {
			msg.setMensaje("Usuario no entontrado :-(");
			msg.setTipo(TIPO_MSG.ERROR);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
		}
		
		NotaEntity notaCreada = null;
		
		nota.setUsuario(usuarioLogueado);
		
		if(result.hasErrors()) {
			msg.setMensaje("Objeto Nota no valido :-(");
			msg.setTipo(TIPO_MSG.ERROR);
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(msg);
		}else {
			notaCreada = notaService.save(nota);
		}
		
		if(notaCreada == null) {
			msg.setMensaje("Ocurrio un error al guardar la nota :-(");
			msg.setTipo(TIPO_MSG.ERROR);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
		}
		
		return ResponseEntity.ok().body(notaCreada);		
				
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateNota(@Valid @RequestBody NotaEntity nota, BindingResult result){
		LOG.info("Nota: " + nota);
		NotaEntity notaActualizada = null;
		
		MensajeResponseModel msg = new MensajeResponseModel();
		
		if(result.hasErrors()) {
			msg.setMensaje("Objeto Nota no valido :-(");
			msg.setTipo(TIPO_MSG.ERROR);
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(msg);
		}else {
			notaActualizada = notaService.save(nota);
		}
		
		if(notaActualizada == null) {
			msg.setMensaje("Ocurrio un error al actualizar la nota :-(");
			msg.setTipo(TIPO_MSG.ERROR);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
		}
		
		return ResponseEntity.ok().body(notaActualizada);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNota(@PathVariable Long id){
		
		MensajeResponseModel msg = new MensajeResponseModel();
		notaService.deleteById(id);		
		msg.setMensaje("Nota eliminada correctamente :-)");
		msg.setTipo(TIPO_MSG.OK);
		return ResponseEntity.ok().body(msg);
	}
}
