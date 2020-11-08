package cn.zhaojian.system.modules.base.serviceimpl;

import cn.zhaojian.system.modules.base.dao.UserRoleDao;
import cn.zhaojian.system.modules.base.entity.UserRole;
import cn.zhaojian.system.modules.base.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class UserRoleServicelmpl implements UserRoleService {

    @Autowired
    public UserRoleDao userRoleDao;

    @Override
    public  UserRoleDao getRepository(){
        return userRoleDao;
    }

    @Override
    public Set<Long> findByUserRoleId(Long userId){
        return userRoleDao.findByUserRoleId(userId);
    }

    @Override
    public List<UserRole> findByUserId(Long userId){
        return userRoleDao.findByUserId(userId);
    }

    @Override
    public void deleteAllByUserIdIn(Set<Long> userIds){
         userRoleDao.deleteAllByUserIdIn(userIds);
    }
}
