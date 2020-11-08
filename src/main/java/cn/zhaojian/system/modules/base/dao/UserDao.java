package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Set;

public interface UserDao extends SystemBaseDao<User,String> {

   User findByUserName(String username);

   User findByCname(String cname);

   User findByEmail(String email);

   User findByPhone(String phone);
   /**
    * 根据Id删除
    * @param ids /
    */
   void deleteAllByIdIn(Set<Long> ids);

}
