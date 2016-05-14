package com.dellnaresh.db.repo;

import com.dellnaresh.db.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by nareshm on 5/14/2016.
 */
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    public List<MovieEntity> findByName(String name);

    public List<MovieEntity> findByUrl(String url);
}
