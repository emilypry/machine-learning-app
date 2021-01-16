package com.wordpress.boxofcubes.machinelearningapp.data;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends CrudRepository<Data, Integer>{
}

