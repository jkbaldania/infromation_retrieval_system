package com.v1.irs.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class BatchService {

    @Autowired
    BatchRepository batchRepository;

    @Transactional
    public Integer saveBatch(Batch batch) {
        Batch saved_batch = batchRepository.save(batch);
        return saved_batch.getBatchId();
    }

    @Transactional
    public List<Batch> findBatchesByUserName(String  userName) {
        List<Batch> batches = batchRepository.findBatchesByUserName(userName);
        return batches;
    }

    @Transactional
    public Batch findBatchByBatchName(String batchName) {
        Batch batch = batchRepository.findBatchByBatchName(batchName);
        return batch;
    }

}
