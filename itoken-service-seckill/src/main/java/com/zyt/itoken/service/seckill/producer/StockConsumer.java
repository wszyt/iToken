package com.zyt.itoken.service.seckill.producer;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zyt.itoken.common.domain.TbSeckill;
import com.zyt.itoken.common.domain.TbSeckillOrder;
import com.zyt.itoken.common.mapper.TbSeckillMapper;
import com.zyt.itoken.common.mapper.TbSeckillOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

/**
 * mq 实现异步修改库存消费者
 * 应该新建一个项目，消费者和生产者不能在同一个服务器
 */
@Component
@Slf4j
public class StockConsumer {
    @Autowired
    private TbSeckillMapper seckillMapper;
    @Autowired
    private TbSeckillOrderMapper orderMapper;

    @RabbitListener(queues = "modify_inventory_queue")
    @Transactional
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        log.info(">>>messageId:{},msg:{}", messageId, msg);
        JSONObject jsonObject = JSONObject.parseObject(msg);
        // 1.获取秒杀id
        Long seckillId = jsonObject.getLong("seckillId");
        TbSeckill seckillEntity = seckillMapper.findBySeckillId(seckillId);
        if (seckillEntity == null) {
            log.warn("seckillId:{},商品信息不存在!", seckillId);
            return;
        }
        Long version = seckillEntity.getVersion();
        int inventoryDeduction = seckillMapper.invenroeyDeduction (seckillId, version);
        if (!toDaoResult(inventoryDeduction)) {
            log.info(">>>seckillId:{}修改库存失败>>>>inventoryDeduction返回为{} 秒杀失败！", seckillId, inventoryDeduction);
            return;
        }
        // 2.添加秒杀订单
        TbSeckillOrder orderEntity = new TbSeckillOrder();
        String phone = jsonObject.getString("phone");
        orderEntity.setUserPhone(Long.parseLong (phone));
        orderEntity.setSeckillId(seckillId);
        orderEntity.setState((byte) 1);
        int insertOrder = orderMapper.insertOrder(orderEntity);
        if (!toDaoResult(insertOrder)) {
            return;
        }
        log.info(">>>修改库存成功seckillId:{}>>>>inventoryDeduction返回为{} 秒杀成功", seckillId, inventoryDeduction);
    }

    // 调用数据库层判断
    public Boolean toDaoResult(int result) {
        return result > 0 ? true : false;
    }

}
