package com.v1.irs.batch;

import javax.persistence.*;

@Entity
public class Batch {

    public Batch() {}

    public Batch(Integer batchId) {
        this.batchId = batchId;
    }

    public Batch(String userName, Integer batchId, String batchName, String batchLocation, String batchIndexLocation) {

        this.userName = userName;
        this.batchId = batchId;
        this.batchName = batchName;
        this.batchLocation = batchLocation;
        this.batchIndexLocation = batchIndexLocation;
    }

    @Column(name="user_name")
    private String userName;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="batch_id")
    private Integer batchId;

    @Column(name="batch_name")
    private String batchName;

    @Column(name="batch_location")
    private String batchLocation;

    @Column(name="batch_index_location")
    private String batchIndexLocation;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchLocation() {
        return batchLocation;
    }

    public void setBatchLocation(String batchLocation) {
        this.batchLocation = batchLocation;
    }

    public String getBatchIndexLocation() {
        return batchIndexLocation;
    }

    public void setBatchIndexLocation(String batchIndexLocation) {
        this.batchIndexLocation = batchIndexLocation;
    }

}
