package ssg.prototype.ssginternshipwebapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ssg.prototype.ssginternshipwebapp.OrdCode;
import ssg.prototype.ssginternshipwebapp.OrdStat;
import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.entity.Variable;
import ssg.prototype.ssginternshipwebapp.domain.repository.OrderRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.VariableRepository;

@Service
public class VariableService {
	private final VariableRepository variableRepository;
	
	public VariableService(VariableRepository variableRepository) {
		this.variableRepository = variableRepository;
	}
	
	public int newOid() {
		Optional<Variable> ocount_ = variableRepository.findById("ocount");
		Variable ocount = ocount_.get(); 
		int newOrderId = ocount.getValue();
		ocount.setValue(newOrderId+1);
		variableRepository.save(ocount);
		return newOrderId;
	}
}
