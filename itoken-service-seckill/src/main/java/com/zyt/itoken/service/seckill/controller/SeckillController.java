package com.zyt.itoken.service.seckill.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyt.itoken.service.seckill.common.BaseResponse;
import com.zyt.itoken.service.seckill.service.SpikeCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeckillController {

    @Autowired
    SpikeCommodityService spikeCommodityService;

    @RequestMapping(value = "/spike")
    public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
        return spikeCommodityService.spike (phone, seckillId);
    }

    @RequestMapping(value = "/add")
    public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity) {
        return  spikeCommodityService.addSpikeToken (seckillId, tokenQuantity);
    }
}
