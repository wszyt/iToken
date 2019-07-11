package com.zyt.itoken.service.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zyt.itoken.service.seckill.common.BaseApiService;
import com.zyt.itoken.service.seckill.common.BaseResponse;
import com.zyt.itoken.service.seckill.common.RedisUtil;
import com.zyt.itoken.service.seckill.service.SpikeCommodityService;
import com.zyt.itoken.common.domain.TbSeckill;
import com.zyt.itoken.common.domain.TbSeckillOrder;
import com.zyt.itoken.common.mapper.TbSeckillMapper;
import com.zyt.itoken.common.mapper.TbSeckillOrderMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {
    @Autowired
    private TbSeckillMapper tbSeckillMapper;

    @Autowired
    private TbSeckillOrderMapper tbSeckillOrderMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    @Transactional
    public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
        // 1.参数验证
        if (StringUtils.isEmpty (phone)) {
            return setResultError("手机号不能为空");
        }
        if (seckillId == null) {
            return setResultError("商品库存id不能为空");
        }


        // 2. 从 redis 中获取对应的秒杀 Token


        // 3. 获取到 token 中放入 mq 中实现修改商品的库存




        /**
        // 2.用户频率验证，做限制
        Boolean aBoolean = redisUtil.setNx (phone, seckillId + "", 10L);
        if (!aBoolean) {
            return setResultError ("访问次数过多，请10秒后再次访问");
        }

        // 100 个商品可以提前生成 100 个 token，谁能抢到 token 谁才能秒杀成功放入 mq 中进行异步减库存（商品抢购）
        // 3.修改数据库对应库存
        TbSeckill entry = tbSeckillMapper.findBySeckillId (seckillId);
        if (entry == null) {
            return setResultError ("商品信息不存在");
        }

        int invenroeyDeduction = tbSeckillMapper.invenroeyDeduction (seckillId, entry.getVersion ());
        if (invenroeyDeduction != 1) {
            return setResultError("请稍后重试");
        }
        // 4.添加秒杀成功订单 可以基于mq实现异步形式
        TbSeckillOrder tbSeckillOrder = new TbSeckillOrder ();
        Long number = Long.parseLong (phone);
        tbSeckillOrder.setUserPhone (number);
        tbSeckillOrder.setSeckillId (seckillId);
        int insert = tbSeckillOrderMapper.insertOrder (tbSeckillOrder);
        if (insert != 1) {
            return setResultError("请稍后重试");
        }
         **/

        return setResultSuccess("抢购成功");
    }
}
