package cn.zhaojian.system.modules.base.service;

import cn.zhaojian.system.base.SystemBaseService;
import cn.zhaojian.system.modules.base.entity.Position;
import cn.zhaojian.system.modules.base.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface UserRoleService extends SystemBaseService<UserRole,String> {
     Set<Long> findByUserRoleId(Long userId);

     List<UserRole> findByUserId(Long userId);

     void deleteAllByUserIdIn(Set<Long> userIds);
}
