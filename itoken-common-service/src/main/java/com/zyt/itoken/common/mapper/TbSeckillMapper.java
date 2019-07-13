package com.zyt.itoken.common.mapper;

import com.zyt.itoken.common.domain.TbSeckill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.MyMapper;

public interface TbSeckillMapper extends MyMapper<TbSeckill> {
    public int invenroeyDeduction(@Param("sekill") long seckill, @Param ("version") long version);

    @Select("SELECT seckill_id AS seckillId,name as name,inventory as inventory,start_time as startTime,end_time as endTime,create_time as createTime,version as version from tb_seckill where seckill_id=#{seckillId}")
    TbSeckill findBySeckillId(Long seckillId);
}