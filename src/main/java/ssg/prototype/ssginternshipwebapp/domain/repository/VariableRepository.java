package ssg.prototype.ssginternshipwebapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssg.prototype.ssginternshipwebapp.domain.entity.Variable;

@Repository
public interface VariableRepository extends JpaRepository<Variable, String> {

}
