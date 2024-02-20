package com.wanli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanli.entity.Comment;
import com.wanli.result.R;


public interface CommentService extends IService<Comment> {
    R comment(Comment addCommentDto);

    R showComment(Long businessId);

    R delComment(Integer commentId);

    R showCommentByAccountId(String accountId);
}
