package com.wanli.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanli.entity.Business;
import com.wanli.entity.Comment;
import com.wanli.entity.Orderdetailet;
import com.wanli.fegin.BusinessServerClient;
import com.wanli.mapper.CommentMapper;
import com.wanli.result.R;
import com.wanli.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Autowired
    private BusinessServerClient businessServerClient;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R comment(Comment comment) {
        if(StringUtils.isBlank(comment.getComment())){
            return R.error(600,"输入为空");
        }
        comment.setAccountId(comment.getAccountId());
        comment.setCreateTime(DateUtil.now());
        if(!save(comment)){
            return R.error(500,"评论失败");
        }
        if(Objects.isNull(comment.getOriginId())){
            BigDecimal grade = commentMapper.getGrade(comment.getBusinessId());
            System.out.println(grade);
            comment.setGrade(grade);

        }
        return R.ok();
    }

    @Override
    public R showComment(Long businessId) {
        List<Comment> list = commentMapper.showComment(businessId);
        //一级评论
        List<Comment> firstCommentList = list.stream().filter(comment -> Objects.isNull(comment.getOriginId())).collect(Collectors.toList());
        for (Comment firstComment: firstCommentList){
            String commentImg = firstComment.getCommentImg();
            System.out.println(commentImg);
            if(!StringUtils.isBlank(commentImg)){
                String[] split = commentImg.split(",");
                System.out.println(Arrays.toString(split));
                firstComment.setCommentImgList(Arrays.asList(split));
            }
            //二级评论
            List<Comment> secondCommentList = list.stream().filter(origin -> firstComment.getId().equals(origin.getOriginId())).collect(Collectors.toList());
            secondCommentList.forEach(item->{
                list.stream().filter(origin -> origin.getId().equals(item.getPid())).findFirst().ifPresent((v->{
                    item.setPname(v.getAccountName());
                }));
            });
            firstComment.setChildren(secondCommentList);
        }
        return R.ok().data("comment",firstCommentList);
    }

    @Override
    public R delComment(Integer commentId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("id",commentId);
        boolean remove = remove(commentQueryWrapper);
        if (!remove) {
            return R.error(500,"删除失败");
        }
        return R.ok();
    }

    @Override
    public R showCommentByAccountId(String accountId) {
        List<Comment> comments = commentMapper.showCommentByAccountId(accountId);
        return R.ok().data("comments",comments);
    }
}
