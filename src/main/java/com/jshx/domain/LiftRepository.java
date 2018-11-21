package com.jshx.domain;

import com.jshx.entity.Lift;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Repository
public interface LiftRepository extends JpaRepository<Lift, String>,JpaSpecificationExecutor<Lift> {

    List<Lift> findAllByRegcodeLike(String regcode);

    Lift findByRegcode(String regcode);


}
