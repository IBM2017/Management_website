package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的DAO
 */
public interface UserDao {

    /**
     *
     * @return
     */
    public List<User> findAll();

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public User findUserByUsernameAndPassword(String username,String password);

    public void add(User user);

    public void delete(int parseInt);

    public User findById(int parseInt);

    public void update(User user);

    public int findTotalCount(Map<String,String[]> condition);

    public List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
