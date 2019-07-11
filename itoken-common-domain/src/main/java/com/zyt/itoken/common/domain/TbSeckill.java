package com.zyt.itoken.common.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_seckill")
public class TbSeckill implements Serializable {
    private static final long serialVersionUID = 595355551522049499L;
    @Column(name = "seckill_id")
    private Long seckillId;

    private String name;

    private Integer inventory;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "create_time")
    private Date createTime;

    private Long version;

    /**
     * @return seckill_id
     */
    public Long getSeckillId() {
        return seckillId;
    }

    /**
     * @param seckillId
     */
    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return inventory
     */
    public Integer getInventory() {
        return inventory;
    }

    /**
     * @param inventory
     */
    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}