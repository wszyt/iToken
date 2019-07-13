package com.zyt.itoken.service.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zyt.itoken.service.seckill.common.BaseApiService;
import com.zyt.itoken.service.seckill.common.BaseResponse;
import com.zyt.itoken.service.seckill.common.GenerateToken;
import com.zyt.itoken.service.seckill.common.RedisUtil;
import com.zyt.itoken.service.seckill.producer.SpikeCommodityProducer;
import com.zyt.itoken.service.seckill.service.SpikeCommodityService;
import com.zyt.itoken.common.domain.TbSeckill;
import com.zyt.itoken.common.mapper.TbSeckillMapper;
import com.zyt.itoken.common.mapper.TbSeckillOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前端可以根据商品 id 查找数据库中的 state 是否为 1 确定是否抢购成功
 */
@Slf4j
@Service
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {

    @Autowired
    private TbSeckillMapper tbSeckillMapper;

    @Autowired
    private TbSeckillOrderMapper tbSeckillOrderMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private SpikeCommodityProducer spikeCommodityProducer;

    @Override
    @Transactional
    public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
        // 1.参数验证
        if (StringUtils.isEmpty (phone)) {
            return setResultError ("手机号不能为空");
        }
        if (seckillId == null) {
            return setResultError ("商品库存id不能为空");
        }


        // 2. 从 redis 中获取对应的秒杀 Token    令牌桶
        String listKeyToken = generateToken.getListKeyToken (seckillId + "");
        if (StringUtils.isEmpty (listKeyToken)) {
            log.info (">>> seckillId:{}, 秒杀已结束", seckillId);
            return setResultError ("秒杀已结束");
        }


        // 3. 获取到 token 中放入 mq 中实现修改商品的库存 使用 rabbitMq 做异步
        sendSeckillMsg (seckillId, phone);


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

        return setResultSuccess ("正在排队中");
    }

    /**
     * 3.获取到 token 中放入 mq 中实现修改商品的库存 使用 rabbitMq 做异步
     */
    @Async
    void sendSeckillMsg(Long seckillId, String phone) {
        JSONObject jsonObject = new JSONObject ();
        jsonObject.put ("seckillId", seckillId);
        jsonObject.put ("phone", phone);
        spikeCommodityProducer.send (jsonObject);
    }


    @Override
    public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity) {
        // 参数验证
        if (seckillId == null) {
            return setResultError ("商品 Id 不能为空");
        }
        if (tokenQuantity == null) {
            return setResultError ("token 数量不能为空");
        }
        TbSeckill bySeckillId = tbSeckillMapper.findBySeckillId (seckillId);
        if (seckillId == null) {
            return setResultError ("商品不存在");
        }
        // 多线程异步生产令牌
        createSeckillToken (seckillId, tokenQuantity);


        return setResultSuccess ("正在生成令牌...");
    }

    @Async
    void createSeckillToken(Long seckillId, Long tokenQuantity) {
        generateToken.createListToken ("seckill_", seckillId + "", tokenQuantity);
    }


}
