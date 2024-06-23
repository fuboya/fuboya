package sourcecode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sourcecode.mapper.UserMapper;
import sourcecode.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createUpdate(User user) {
        User dbuser = userMapper.findByAccountId(user.getAccount_id());
        if(dbuser==null){
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }else{
            dbuser.setGmt_modified(System.currentTimeMillis());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            userMapper.update(dbuser);
        }
    }
}
