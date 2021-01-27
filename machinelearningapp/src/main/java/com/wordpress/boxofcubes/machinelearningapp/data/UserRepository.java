package com.wordpress.boxofcubes.machinelearningapp.data;

import java.util.List;
import java.util.Optional;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;
import com.wordpress.boxofcubes.machinelearningapp.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    public Optional<User> findByUsername(String username);
}
