package com.zyt.itoke.service.seckill.service;

import com.alibaba.fastjson.JSONObject;
import com.zyt.itoke.service.seckill.common.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

// 秒杀接口
public interface SpikeCommodityService {

    /**
     * 用户秒杀接口
     * @param phone 手机号码或者 userId
     * @param seckillId
     * @return
     */
    @RequestMapping("/spike")
    public BaseResponse<JSONObject> spike(String phone, Long seckillId);
}
