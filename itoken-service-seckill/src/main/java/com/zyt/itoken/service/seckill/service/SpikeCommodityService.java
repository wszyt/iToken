package com.zyt.itoken.service.seckill.service;

import com.alibaba.fastjson.JSONObject;
import com.zyt.itoken.service.seckill.common.BaseResponse;

// 秒杀接口
public interface SpikeCommodityService {

    /**
     * 用户秒杀接口
     * @param phone 手机号码或者 userId
     * @param seckillId
     * @return
     */
    public BaseResponse<JSONObject> spike(String phone, Long seckillId);


    /**
     * 给秒杀商品生成一定数量的 token 到 redis 中
     * @param seckillId
     * @param tokenQuantity
     * @return
     */
    public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity);
}
