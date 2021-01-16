package com.wordpress.boxofcubes.machinelearningapp.data;

import com.wordpress.boxofcubes.machinelearningapp.models.DataValue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataValueRepository extends CrudRepository<DataValue, Integer>{
}
