package com.easyjava.controller;

import com.easyjava.entity.po.UserInfo;
import com.easyjava.entity.query.UserInfoQuery;
import com.easyjava.entity.vo.ResponseVO;
import com.easyjava.service.UserInfoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;
/**
 * @Description:用户信息Controller
 * @author:人防王中王
 * @date:2025/01/15
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends ABaseController{

	@Resource
	private UserInfoService userInfoService;

	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(UserInfoQuery query) {
		return getSuccessResponseVO(userInfoService.findListByPage(query));
	}
	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(UserInfo bean){
		this.userInfoService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<UserInfo> listBean){
		this.userInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<UserInfo> listBean){
		this.userInfoService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId查询
	 */
	@RequestMapping("getUserInfoByUserId")
	public ResponseVO getUserInfoByUserId(Integer userId){
		return getSuccessResponseVO(this.userInfoService.getUserInfoByUserId(userId));
	}
	/**
	 * 根据UserId更新
	 */
	@RequestMapping("updateUserInfoByUserId")
	public ResponseVO updateUserInfoByUserId(@RequestBody UserInfo bean, Integer userId){
		this.userInfoService.updateUserInfoByUserId(bean,userId);
		return getSuccessResponseVO(null);
	}
	/**
	 * 根据UserId删除
	 */
	@RequestMapping("deleteUserInfoByUserId")
	public ResponseVO deleteUserInfoByUserId(Integer userId){
		this.userInfoService.deleteUserInfoByUserId(userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email查询
	 */
	@RequestMapping("getUserInfoByEmail")
	public ResponseVO getUserInfoByEmail(String email){
		return getSuccessResponseVO(this.userInfoService.getUserInfoByEmail(email));
	}
	/**
	 * 根据Email更新
	 */
	@RequestMapping("updateUserInfoByEmail")
	public ResponseVO updateUserInfoByEmail(@RequestBody UserInfo bean, String email){
		this.userInfoService.updateUserInfoByEmail(bean,email);
		return getSuccessResponseVO(null);
	}
	/**
	 * 根据Email删除
	 */
	@RequestMapping("deleteUserInfoByEmail")
	public ResponseVO deleteUserInfoByEmail(String email){
		this.userInfoService.deleteUserInfoByEmail(email);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据NickName查询
	 */
	@RequestMapping("getUserInfoByNickName")
	public ResponseVO getUserInfoByNickName(String nickName){
		return getSuccessResponseVO(this.userInfoService.getUserInfoByNickName(nickName));
	}
	/**
	 * 根据NickName更新
	 */
	@RequestMapping("updateUserInfoByNickName")
	public ResponseVO updateUserInfoByNickName(@RequestBody UserInfo bean, String nickName){
		this.userInfoService.updateUserInfoByNickName(bean,nickName);
		return getSuccessResponseVO(null);
	}
	/**
	 * 根据NickName删除
	 */
	@RequestMapping("deleteUserInfoByNickName")
	public ResponseVO deleteUserInfoByNickName(String nickName){
		this.userInfoService.deleteUserInfoByNickName(nickName);
		return getSuccessResponseVO(null);
	}

}