package com.v1.irs.batch;

import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import com.v1.irs.batch.Batch;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Integer> {

    List<Batch> findBatchesByUserName(String userName);

}
