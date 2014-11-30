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
		
	public Bus findById(Long id) {
		Query query = query(where("id").is(id));
		return operations.findOne(query, Bus.class);
	}

	public Bus findByNome(String nome) {		
		Query query = query(where("nome").is(nome));
		return operations.findOne(query, Bus.class);
	}
	
	public Bus save(Bus bus) {
		operations.save(bus);
		linhaRepository.save(bus.getLinha());
		return bus;
	}

	public List<Bus> findAll() {
		return operations.findAll(Bus.class);
	}

	public List<Bus> findAllByLinha(Long idLinha) {
		Query query = query(where("linha.id").is(idLinha));
		return operations.find(query, Bus.class);
	}

	public List<Bus> findAllByPontoAndLinha(long idPonto, long idLinha) {
		Criteria where = where("linha.rota._id").is(idPonto);
		if (idLinha > 0) {
			where.and("linha.idLinha").is(idLinha);
		}
		Query query = query(where).with(new Sort(Sort.Direction.ASC,"linha.roda.tempo"));
		return operations.find(query, Bus.class);
	}
			
}
