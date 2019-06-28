package com.zyt.itoken.common.mapper;

import com.zyt.itoken.common.domain.TbSysUser;
import com.zyt.itoken.common.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.MyMapper;

@CacheNamespace(implementation = RedisCache.class)
public interface TbSysUserMapper extends MyMapper<TbSysUser> {
}