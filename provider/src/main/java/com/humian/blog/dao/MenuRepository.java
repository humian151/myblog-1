package com.humian.blog.dao;

import com.humian.blog.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by DELL on 2018/8/28.
 */
public interface MenuRepository  extends JpaRepository<Menu,Long>{
    @Query("SELECT DISTINCT m FROM  Menu m ,RoleMenu rm ,UserRole ur  WHERE m.id = rm.menuId AND rm.roleId = ur.roleId AND ur.id = :userid")
    List<Menu> findMenuByUserId(@Param("userid") Long userid);
}
