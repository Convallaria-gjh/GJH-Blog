package com.example.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.TbPost;
import com.example.mapper.TbPostMapper;
import com.example.service.TbPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.util.RedisUtil;
import com.example.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
@Service
public class TbPostServiceImpl extends ServiceImpl<TbPostMapper, TbPost> implements TbPostService {

    @Autowired
    TbPostMapper postMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {

        if(level == null) { level = -1; }

        QueryWrapper<TbPost> wrapper = new QueryWrapper<TbPost>()
                .eq(categoryId != null,"category_id",categoryId)
                .eq(userId != null,"user_id",userId)
                .eq(level == 0,"level",0)
                .gt(level > 0,"level",0)
                .orderByDesc(order != null,order);
        return postMapper.selectPosts(page,wrapper);
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<TbPost> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }

    @Override
    public void initWeekRank() {
        //获取7天发表的文章
        List<TbPost> posts = this.list(new QueryWrapper<TbPost>()
                .ge("created", DateUtil.offsetDay(new Date(),-7))
                .select("id,title,user_id,comment_count,view_count,created")
        );
        //初始化文章的总评数量
        for (TbPost post : posts) {
            String key = "day:rank:" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);
            redisUtil.zSet(key,post.getId(),post.getCommentCount());

            //有效时间
            long between = DateUtil.between(new Date(),post.getCreated(), DateUnit.DAY);
            long expireTime = (7 - between) * 24 * 60 * 60;     //有效时间

            redisUtil.expire(key,expireTime);

            //缓存文章的基本信息
            this.hashCachePostIdAndTitle(post,expireTime);
        }
        
        this.zunionAndStoreLastSevenDatForWeeKRank();
    }

    /**
     * 合并数据
     */
    private void zunionAndStoreLastSevenDatForWeeKRank() {
        String currentKey = "day:rank:" + DateUtil.format(new Date(),DatePattern.PURE_DATE_FORMAT);

        String destKey = "week:rank";
        List<String> otherKeys = new ArrayList<>();
        for (int i = -6; i < 0; i++) {
            String temp = "day:rank:" +
                    DateUtil.format(DateUtil.offsetDay(new Date(),i),DatePattern.PURE_DATE_FORMAT);
            otherKeys.add(temp);
        }
        redisUtil.zUnionAndStore(currentKey,otherKeys,destKey);
    }

    /**
     * 缓存文章的基本信息
     * @param post
     * @param expireTime
     */
    private void hashCachePostIdAndTitle(TbPost post, long expireTime) {
        String key = "rank:post:" + post.getId();
        boolean hashKey = redisUtil.hasKey(key);
        if(!hashKey) {      //判断是否有key
            redisUtil.hset(key,"post:Id",post.getId(),expireTime);
            redisUtil.hset(key,"post:title",post.getTitle(),expireTime);
            redisUtil.hset(key,"post:commentCount",post.getCommentCount(),expireTime);
            redisUtil.hset(key,"post:viewCount",post.getViewCount(),expireTime);
        }
    }

    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {
        String  currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        redisUtil.zIncrementScore(currentKey, postId, isIncr? 1: -1);

        TbPost post = this.getById(postId);

        // 7天后自动过期(15号发表，7-（18-15）=4)
        long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        long expireTime = (7 - between) * 24 * 60 * 60; // 有效时间

        // 缓存这篇文章的基本信息
        this.hashCachePostIdAndTitle(post, expireTime);

        // 重新做并集
        this.zunionAndStoreLastSevenDatForWeeKRank();
    }

    @Override
    public void putViewCount(PostVo vo) {
        String key = "rank:post:" + vo.getId();


        // 1、从缓存中获取viewcount
        Integer viewCount = (Integer) redisUtil.hget(key, "post:viewCount");

        // 2、如果没有，就先从实体里面获取，再加一
        if(viewCount != null) {
            vo.setViewCount(viewCount + 1);
        } else {
            vo.setViewCount(vo.getViewCount() + 1);
        }

        // 3、同步到缓存里面
        redisUtil.hset(key, "post:viewCount", vo.getViewCount());

    }

}
