package core.model;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PontoRepository {

	@Autowired
	private MongoOperations operations;

	public Ponto findById(Long id) {
		Query query = query(where("_id").is(id));
		return operations.findOne(query, Ponto.class);
	}

	public List<Ponto> findAll() {
		return operations.findAll(Ponto.class);
	}
	public Ponto save(Ponto ponto) {
		operations.save(ponto);
		return ponto;
	}

	public List<Ponto> findByLinha(long idLinha) {
		Criteria where = where("linhaId").is(idLinha);
		Query query = query(where);
		return operations.find(query, Ponto.class);
	}
	
	
}
