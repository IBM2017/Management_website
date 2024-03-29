package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    /**
     *
     * @param user
     * @return
     */
    public User login(User user);

    /**
     *
     * @param user
     */
    public void addUser(User user);

    /**
     *
     * @param id
     */
    public void deleteUser(String id);

    public User findUserById(String id);

    public void updateUser(User user);

    public void delSelectedUser(String[] ids);

    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
