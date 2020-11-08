package cn.zhaojian.system.modules.base.dao;

import cn.zhaojian.system.base.SystemBaseDao;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface UserRoleDao extends SystemBaseDao<UserRole,String> {
    @Query(value = "select role_id from t_user_role where user_id = ?1",nativeQuery = true)
     Set<Long> findByUserRoleId(Long userId);

    @Query(value = "select * from t_user_role where user_id = ?1",nativeQuery = true)
    List<UserRole> findByUserId(Long userId);
     void deleteAllByUserIdIn(Set<Long> userIds);
}
