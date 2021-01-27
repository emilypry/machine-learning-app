package com.wordpress.boxofcubes.machinelearningapp.data;

import java.util.List;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.SavingModel;
import com.wordpress.boxofcubes.machinelearningapp.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends CrudRepository<Data, Integer>{
    public List<Data> findByUser(User user);
}

