package core.model;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BusRepository {
	
	@Autowired
	private MongoOperations operations;
	
	@Autowired
	private LinhaRepository linhaRepository;
		
	public Bus findById(Integer id) {
		Query query = query(where("id").is(id));
		return operations.findOne(query, Bus.class);
	}

	public Bus findByNome(String nome) {		
		Query query = query(where("nome").is(nome));
		return operations.findOne(query, Bus.class);
	}
	
	public Bus save(Bus bus) {
		operations.save(bus);
		return bus;
	}

	public List<Bus> findAll() {
		return operations.findAll(Bus.class);
	}

	public List<Bus> findAllByLinha(Integer idLinha) {
		Query query = query(where("linhaId").is(idLinha));
		return operations.find(query, Bus.class);
	}
	
	public List<Bus> findAllByPontoAndLinha(Integer idPonto, Integer idLinha) {
		Criteria where = where("rota._id").is(idPonto);
		if (idLinha > 0) {
			where.and("_id").is(idLinha);
		}
		Query query = query(where).with(new Sort(Sort.Direction.ASC,"rota.tempo"));
		List<Linha> linhas = operations.find(query, Linha.class);
		Query busQuery = query(where("linhaId").in(linhas));
		return operations.find(busQuery, Bus.class);
	}

			
}
