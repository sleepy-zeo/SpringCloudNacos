package com.lullaby.raw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lullaby.raw.domain.User;
import com.lullaby.raw.service.UserService;
import com.lullaby.raw.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, IService<User> {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        Optional<User> optionalUser = Optional.ofNullable(select(user.getUid()));
        if (optionalUser.equals(Optional.empty())) {
            userMapper.insert(user);
        }
    }

    @Override
    public void deleteUser(int uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        userMapper.delete(queryWrapper);
    }

    @Override
    public void update(User user) {
        Optional.ofNullable(select(user.getUid()))
                .ifPresent(e -> userMapper.update(user, new QueryWrapper<User>().eq("uid", user.getUid())));
    }

    @Override
    public User select(int uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Set<User> selectUsers(List<Integer> uids) {
        return userMapper.selectUsersWithList(uids);
    }

    @Override
    public User selectUser(Map<String, Object> pairs) {
        return userMapper.selectUser(pairs);
    }

    @Override
    public Set<User> selectUsers_(Map<String, Object> pairs) {
        return userMapper.selectUsersWithMap(pairs);
    }

    public void queryWrapperApis() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // equal
        queryWrapper.eq("name", "steven");
        // name = "steven" and age = 24
        queryWrapper.eq("name", "steven").eq("age", 24);
        // not equal
        queryWrapper.ne("name", "steven");
        // greater than
        queryWrapper.gt("age", 18);
        // not lower than
        queryWrapper.ge("age", 18);
        // lower than
        queryWrapper.lt("age", 62);
        // not greater than
        queryWrapper.le("age", 62);
        // between
        queryWrapper.between("uid", 100, 108);
        // not between
        queryWrapper.notBetween("uid", 100, 108);
        // like %scn%
        queryWrapper.like("name", "scn");
        // not like %scn%
        queryWrapper.notLike("name", "scn");
        // like %scn
        queryWrapper.likeLeft("name", "scn");
        // like scn%
        queryWrapper.likeRight("name", "scn");
        // in (11, 13, ..., 37)
        queryWrapper.in("age", 11, 13, 17, 19, 23, 29, 31, 37);
        // not in (11, 13, ..., 37)
        queryWrapper.notIn("age", 11, 13, 17, 19, 23, 29, 31, 37);

        // or的简单用法
        queryWrapper.eq("app_type", "wechat")
                .or()
                .eq("app_code", "M109733");

        // where actor_id > 10 AND (first_name LIKE '%A%' OR last_name LIKE '%A%');
        queryWrapper.ge("actor_id", 10).and(Wrapper -> Wrapper.like("first_name", "mama").or().like("last_name ", "mama"));

        // where actor_id > 10 OR (first_name LIKE '%A%' AND last_name LIKE '%A%');
        queryWrapper.ge("actor_id", 10).or(Wrapper -> Wrapper.like("first_name", "mama").like("last_name ", "mama"));

    }
}
