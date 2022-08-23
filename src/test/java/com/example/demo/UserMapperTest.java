package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
@SpringBootTest
class UserMapperTest {
	@Resource
	private UserMapper userMapper;

	@Test
	public void testCRUD() {
		// 插入
		userMapper.insert(new User(6L,"小红",18,"xiaohong@qq.com"));
		// 查询所有
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
		// 指定条件查询
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",5).or()
						.eq("id",2);
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
		// 更新
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		User user = new User();
		user.setAge(19);
		updateWrapper.like("email","test").or().between("age",20,25);
		int update = userMapper.update(user, updateWrapper);
		System.out.println(update);
		userList = userMapper.selectList(null);
		System.out.println(userList);
		// 删除
		int i = userMapper.deleteById(1);
		user.setId(6L);
		int i1 = userMapper.deleteById(user);
		System.out.println(i);
		userList = userMapper.selectList(null);
		System.out.println(userList);
	}

}
