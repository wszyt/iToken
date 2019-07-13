package com.zyt.itoken.common.mapper;

import com.zyt.itoken.common.domain.TbSeckillOrder;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.MyMapper;

public interface TbSeckillOrderMapper extends MyMapper<TbSeckillOrder> {
    @Insert("INSERT INTO `tb_seckill_order` VALUES (#{seckillId},#{userPhone}, '1', now());")
    int insertOrder(TbSeckillOrder tbSeckillOrder);
}