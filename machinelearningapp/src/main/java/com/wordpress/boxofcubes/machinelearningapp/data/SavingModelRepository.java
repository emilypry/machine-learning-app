package com.wordpress.boxofcubes.machinelearningapp.data;

import java.util.List;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.SavingModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingModelRepository extends CrudRepository<SavingModel, Integer>{
    public List<SavingModel> findByData(Data data);
}
