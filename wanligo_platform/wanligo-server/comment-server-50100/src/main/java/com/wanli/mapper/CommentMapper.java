package com.wanli.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanli.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Byterain
 * @since 2023-12-27
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("SELECT t1.*,t2.account_name,t2.account_img from sys_comment t1 " +
            "LEFT JOIN sys_account t2 on t1.account_id = t2.account_id where t1.business_id=#{business_id} order by t1.create_time asc")
    List<Comment> showComment(@Param("business_id") Long businessId);

    @Select("select AVG(grade) from sys_comment where business_id=#{business_id}")
    BigDecimal getGrade(@Param("business_id") Long businessId);

    @Select("SELECT * FROM sys_comment WHERE account_id = #{account_id} AND pid is null and origin_id is null")
    List<Comment> showCommentByAccountId(@Param("account_id") String accountId);
}