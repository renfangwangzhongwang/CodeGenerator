package com.easyjava.service;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easyjava.enums.DateTimePatternEnum;
import com.easyjava.utils.DateUtils;
import com.easyjava.entity.vo.PaginationResultVO;
import com.easyjava.entity.po.UserInfo;
import com.easyjava.entity.query.UserInfoQuery;

import java.util.List;
/**
 * @Description:用户信息Service
 * @author:人防王中王
 * @date:2025/01/15
 */
public interface UserInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<UserInfo> findListByParam(UserInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(UserInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<UserInfo> findListByPage(UserInfoQuery query);

	/**
	 * 新增
	 */
	Integer add(UserInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserInfo> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<UserInfo> listBean);

	/**
	 * 根据UserId查询
	 */
	UserInfo getUserInfoByUserId(Integer userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateUserInfoByUserId(UserInfo bean, Integer userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteUserInfoByUserId(Integer userId);

	/**
	 * 根据Email查询
	 */
	UserInfo getUserInfoByEmail(String email);

	/**
	 * 根据Email更新
	 */
	Integer updateUserInfoByEmail(UserInfo bean, String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteUserInfoByEmail(String email);

	/**
	 * 根据NickName查询
	 */
	UserInfo getUserInfoByNickName(String nickName);

	/**
	 * 根据NickName更新
	 */
	Integer updateUserInfoByNickName(UserInfo bean, String nickName);

	/**
	 * 根据NickName删除
	 */
	Integer deleteUserInfoByNickName(String nickName);

}