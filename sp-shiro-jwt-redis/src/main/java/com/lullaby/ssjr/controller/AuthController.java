package com.lullaby.ssjr.controller;

import com.lullaby.ssjr.common.Constants;
import com.lullaby.ssjr.common.CustomException;
import com.lullaby.ssjr.common.ResponseResult;
import com.lullaby.ssjr.common.entity.User;
import com.lullaby.ssjr.config.redis.JedisTemplate;
import com.lullaby.ssjr.utils.AesCipherUtil;
import com.lullaby.ssjr.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private JedisTemplate jedisTemplate;
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User userDto, HttpServletResponse httpServletResponse) {
        // 查询数据库中的帐号信息
        // 测试账号 233452@outlook.com 00001111
        User userTemp = new User();
        userTemp.setAccount("233452@outlook.com");
        userTemp.setPassword("Rjk2MDVEM0E2NEE1NDFGN0U5OEI4NDU0NUFENDVBOUNCRkExOTc3RTgzNDQ5NTM2NEI0MjkxMDc2NkZBRTNFNQ==");
        if (userTemp == null) {
            throw new CustomException("该帐号不存在(The account does not exist.)");
        }

        // 数据库中保存的密码是(1.account+password 2.aes加密)
        // 所以验证的时候是(1.aes解码数据库中的密码 2.和前端传过来的account+password进行比对)
        String key = AesCipherUtil.deCrypto(userTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是前端传过来的帐号+密码
        if (key.equals(userDto.getAccount() + userDto.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (jedisTemplate.exists(Constants.PREFIX_SHIRO_CACHE + userDto.getAccount())) {
                jedisTemplate.delKey(Constants.PREFIX_SHIRO_CACHE + userDto.getAccount());
            }
            // 设置RefreshToken，值为当前时间戳
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            String token = JwtUtil.generateToken(userDto.getAccount());
            return new ResponseResult(HttpStatus.OK.value(), "登录成功(Login Success.)", token);
        } else {
            throw new CustomException("帐号或密码错误(Account or Password Error.)");
        }
    }
}
