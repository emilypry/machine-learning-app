package com.wordpress.boxofcubes.machinelearningapp.data;

import com.wordpress.boxofcubes.machinelearningapp.models.SavingModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingModelRepository extends CrudRepository<SavingModel, Integer>{
}
