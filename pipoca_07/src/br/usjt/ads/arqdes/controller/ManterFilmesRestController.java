package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

@RestController
public class ManterFilmesRestController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;

	@RequestMapping(method = RequestMethod.POST, value = "rest/filme")
	public ResponseEntity<Filme> inserirFilme(@RequestBody Filme filme) {
		try {
			filme = fService.inserirFilme(filme);
			return new ResponseEntity(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "rest/filme")
	public ResponseEntity<List<Filme>> buscarFilmes() {
		try {
			List<Filme> filmes = fService.listarFilmes();
			return new ResponseEntity(filmes, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "rest/filme/{id}")
	public ResponseEntity<Filme> buscarFilme(@PathVariable("id") Long id) {
		try {
			Filme filme = fService.buscarFilme(id.intValue());
			return new ResponseEntity(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
