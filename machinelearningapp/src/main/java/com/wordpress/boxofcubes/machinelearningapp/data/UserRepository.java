package com.wordpress.boxofcubes.machinelearningapp.data;

import com.wordpress.boxofcubes.machinelearningapp.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
}
