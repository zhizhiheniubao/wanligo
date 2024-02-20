package com.wanli.serversystem2023.mapper;

import com.wanli.serversystem2023.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    //自定义
    public List<Long> getNavMenuIds(Long user_id);

    public List<User> listByMenuId(Long menu_id);
}
