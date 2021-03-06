package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    //@Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).file)}")
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).inputStream)}")
    public String addNewUserSql;

    //@Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${delete_all_users_path}')).file)}")
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete_user_path}')).inputStream)}")
    public String deleteUserSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete_all_users_like_login_path}')).inputStream)}")
    public String deleteAllUserLikeLoginSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_user_path}')).inputStream)}")
    public String updateUserSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_user_by_login_path}')).inputStream)}")
    public String selectUserByLoginSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_user_by_id_path}')).inputStream)}")
    public String selectUserByIdSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_user_by_name_path}')).inputStream)}")
    public String selectUserByNameSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_users_path}')).inputStream)}")
    public String selectAllUsersSql;

    public static final String USER_ID = "userid";
    public static final String LOGIN = "login";
    public static final String USERNAME = "username";

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @PostConstruct
    public void init(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addUser(User user) {
        LOGGER.debug("addUser({}) ", user);
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getUserName(), "User name should be specified.");
        Map<String, Object> parameters = new HashMap(3);
        parameters.put(USERNAME, user.getUserName());
        parameters.put(LOGIN, user.getLogin());
        parameters.put(USER_ID, user.getUserId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(addNewUserSql, new MapSqlParameterSource(parameters), keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("get users()");
        return jdbcTemplate.query(selectAllUsersSql, new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser(userId={})",userId);
        jdbcTemplate.update(deleteUserSql, userId);
    }

    @Override
    public void removeAllUser(String login) {
        LOGGER.debug("removeAllUser(login={})",login);
        jdbcTemplate.update(deleteAllUserLikeLoginSql+login.toLowerCase()+"%' ");
    }

    @Override
    public User getUserByLogin(String login){
        LOGGER.debug("getUserByLogin(login={}) ", login);
        return jdbcTemplate.queryForObject(selectUserByLoginSql,
                new String[]{login.toLowerCase()}, new UserMapper());
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById(userId={})", userId);
        return jdbcTemplate.queryForObject(selectUserByIdSql, new UserMapper(),userId);
    }

    @Override
    public User getUserByUserName(String username){
        LOGGER.debug("getUserByUserName(username={})", username);
        return jdbcTemplate.queryForObject(selectUserByNameSql, new UserMapper(),username);
    }

    @Override
    public void updateUser(User user){
        KeyHolder keyholder = new GeneratedKeyHolder();
        LOGGER.debug("updateUser({})", user);
        Map<String, Object> parameters = new HashMap(3);
            parameters.put(USER_ID, user.getUserId());
            parameters.put(LOGIN,user.getLogin());
            parameters.put(USERNAME, user.getUserName());
        namedJdbcTemplate.update(updateUserSql,new MapSqlParameterSource(parameters),keyholder);
        LOGGER.debug("updateUser(): id{}",keyholder.getKey());
       
    }

    public class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(LOGIN));
            user.setUserName(rs.getString(USERNAME));
            return user;
        }

    }
}
