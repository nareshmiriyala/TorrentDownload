package com.dellnaresh.db.repo;

import com.dellnaresh.db.model.MovieEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nareshm on 5/14/2016.
 */
public interface MovieRepository extends CrudRepository<MovieEntity,Long> {
    public List<MovieEntity> findByName(String name);
    public List<MovieEntity> findByUrl(String url);
}
