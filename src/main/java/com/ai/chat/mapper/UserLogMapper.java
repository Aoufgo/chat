package com.ai.chat.mapper;

import com.ai.chat.pojo.UserLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aoufgo
 * @date 2021/5/10 下午10:24
 */
@Repository
public interface UserLogMapper {
    /**
     * 保存日志
     * @param log
     * @return
     */
    int add (UserLog log);

    /**
     * 查询所有日志
     * @return
     */
    List<UserLog> queryAllLog ();
}
