package com.wanli.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.entity.Comment;
import com.wanli.result.R;
import com.wanli.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    @GetMapping("/infoByAccountId/{accountId}")
    public R showCommentByAccountId(@PathVariable("accountId") String accountId){
        return commentService.showCommentByAccountId(accountId);
    }
    //通过用户返回评论数量
    @GetMapping("/countByAccount/{accountId}")
    public R countByAccount(@PathVariable("accountId") String accountId){
        int count = commentService.count(new QueryWrapper<Comment>().eq("account_id", accountId));
        return R.ok().data("comment",count);
    }
    //评论功能
    @GetMapping("/info/{businessId}")
    public R showComment(@PathVariable("businessId") Long businessId){
        return commentService.showComment(businessId);
    }

    @PostMapping("/addComment")
    public R comment(@RequestBody Comment addCommentDto){
        return commentService.comment(addCommentDto);
    }

    @DeleteMapping("/delComment/{commentId}")
    public R delComment(@PathVariable Integer commentId){
        return commentService.delComment(commentId);
    }
}
