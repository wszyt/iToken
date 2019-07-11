package com.zyt.itoken.common.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_seckill_order")
public class TbSeckillOrder implements Serializable {
    private static final long serialVersionUID = 4407269770636526206L;
    @Id
    @Column(name = "seckill_id")
    private Long seckillId;

    @Column(name = "user_phone")
    private Long userPhone;

    private Long state;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return user_phone
     */
    public Long getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone
     */
    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return state
     */
    public Long getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Long state) {
        this.state = state;
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
}