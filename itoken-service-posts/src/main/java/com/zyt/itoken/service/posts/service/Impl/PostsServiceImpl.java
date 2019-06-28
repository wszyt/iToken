package com.zyt.itoken.service.posts.service.Impl;

import com.zyt.itoken.common.domain.TbPostsPost;
import com.zyt.itoken.common.mapper.TbPostsPostMapper;
import com.zyt.itoken.common.service.Impl.BaseServiceImpl;
import com.zyt.itoken.service.posts.service.PostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostsServiceImpl extends BaseServiceImpl<TbPostsPost, TbPostsPostMapper> implements PostsService {
}
