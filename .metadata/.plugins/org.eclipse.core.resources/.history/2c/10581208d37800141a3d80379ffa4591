package core.model;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LinhaRepository {
	
	@Autowired
	private MongoOperations operations;
	
	@Autowired
	private PontoRepository pontoRepository;

	public Linha findById(Long id) {
		Query query = query(where("id").is(id));
		return operations.findOne(query, Linha.class);
	}

	public Linha findByNome(String nome) {		
		Query query = query(where("nome").is(nome));
		return operations.findOne(query, Linha.class);
	}
	
	public Linha save(Linha linha) {
		operations.save(linha);
		for (Ponto ponto : linha.getPontosNaLinha()) {
			pontoRepository.save(ponto);
		}
		return linha;
	}

	public List<Linha> findAll() {
		return operations.findAll(Linha.class);
	}
			
}
