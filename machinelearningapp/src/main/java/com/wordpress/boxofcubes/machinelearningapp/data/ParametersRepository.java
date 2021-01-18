package com.wordpress.boxofcubes.machinelearningapp.data;

import com.wordpress.boxofcubes.machinelearningapp.models.Parameters;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersRepository extends CrudRepository<Parameters, Integer>{
}
