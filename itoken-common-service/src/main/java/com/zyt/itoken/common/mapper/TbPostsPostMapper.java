package com.zyt.itoken.common.mapper;

import com.zyt.itoken.common.domain.TbPostsPost;
import com.zyt.itoken.common.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Qualifier;
import tk.mybatis.mapper.MyMapper;

@CacheNamespace(implementation = RedisCache.class)
public interface TbPostsPostMapper extends MyMapper<TbPostsPost> {
}