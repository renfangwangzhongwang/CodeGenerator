package com.easyjava.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
/**
 * @Description:用户信息Mapper
 * @author:人防王中王
 * @date:2025/01/15
 */
public interface UserInfoMapper<T, P> extends BaseMapper{
	/**
	 * 根据UserId查询
	 */
	T selectByUserId(@Param("userId") Integer userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(@Param("bean") T t, @Param("userId") Integer userId);

	/**
	 * 根据UserId查询
	 */
	Integer deleteByUserId(@Param("userId") Integer userId);

	/**
	 * 根据Email查询
	 */
	T selectByEmail(@Param("email") String email);

	/**
	 * 根据Email更新
	 */
	Integer updateByEmail(@Param("bean") T t, @Param("email") String email);

	/**
	 * 根据Email查询
	 */
	Integer deleteByEmail(@Param("email") String email);

	/**
	 * 根据NickName查询
	 */
	T selectByNickName(@Param("nickName") String nickName);

	/**
	 * 根据NickName更新
	 */
	Integer updateByNickName(@Param("bean") T t, @Param("nickName") String nickName);

	/**
	 * 根据NickName查询
	 */
	Integer deleteByNickName(@Param("nickName") String nickName);

}