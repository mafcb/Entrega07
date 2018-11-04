package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.entity.Movie;
import br.usjt.ads.arqdes.model.entity.Resultado;
@Service
public class FilmeService {
	public static final String API_KEY = "47ba2c94785cd2d5dfa8952618552499";
	public static final String URL ="https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=pt-BR&page=1&region=BR";
	
	@Autowired
	private FilmeDAO dao;
	
	public Filme buscarFilme(int id) throws IOException{
		return dao.buscarFilme(id);
	}
	
	@Transactional
	public Filme inserirFilme(Filme filme) throws IOException {
		int id = dao.inserirFilme(filme);
		filme.setId(id);
		return filme;
	}

	public List<Filme> listarFilmes(String chave) throws IOException{
		return dao.listarFilmes(chave);
	}

	public List<Filme> listarFilmes() throws IOException{
		return dao.listarFilmes();
	}
	
	@Transactional
	public void atualizarFilme(Filme filme) throws IOException {
		dao.atualizarFilme(filme);
	}
	
	@Transactional
	public void excluirFilme(int id) throws IOException {
		dao.excluirFilme(id);
	}
 
	@Transactional
	public void baixarFilmesPopulares() throws IOException {
		
		RestTemplate rest = new RestTemplate();
		Resultado retorno = rest.getForObject(URL, Resultado.class);
		System.out.println("Retorno: " + retorno);
		for(Movie movie: retorno.getResults()) {
			Filme filme = new Filme();
			filme.setTitulo(movie.getTitle());
			filme.setDataLancamento(movie.getRelease_date());
			filme.setDescricao(movie.getOverview());
			Genero genero = new Genero();
			genero.setId(movie.getGenre_ids()[0]);
			filme.setGenero(genero);
			filme.setPopularidade(movie.getPopularity());
			filme.setPosterPath("https://image.tmdb.org/t/p/w400"+movie.getPoster_path());
			dao.inserirFilme(filme);
			
		}
	}
	
}
