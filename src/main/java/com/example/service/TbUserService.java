package com.example.service;

import com.example.common.lang.Result;
import com.example.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Nigori
 * @since 2020-06-03
 */
public interface TbUserService extends IService<TbUser> {

    Result register(TbUser user);

    AccountProfile login(String username, String password);
}
