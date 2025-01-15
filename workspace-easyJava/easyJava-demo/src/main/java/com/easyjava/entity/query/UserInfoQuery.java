package com.easyjava.entity.query;

import java.util.Date;
/**
 * @Description:用户信息查询对象
 * @author:人防王中王
 * @date:2025/01/15
 */
public class UserInfoQuery extends BaseQuery{
	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 0：女 1：男 2：未知性别
	 */
	private Integer sex;

	/**
	 * 出生日期
	 */
	private String birthday;

	/**
	 * 学校
	 */
	private String school;

	/**
	 * 个人简介
	 */
	private String personIntroduction;

	/**
	 * 注册时间
	 */
	private Date joinTime;

	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 最近登录ip
	 */
	private String lastLoginIp;

	/**
	 * 0:禁用1:正常
	 */
	private String status;

	/**
	 * 空间公告
	 */
	private String noticeInfo;

	/**
	 * 硬币总数量
	 */
	private Integer totalCoinCount;

	/**
	 * 当前硬币数
	 */
	private Integer currentCoinCount;

	/**
	 * 主题
	 */
	private Integer theme;

	/**
	 * 昵称——模糊匹配（扩展）
	 */
	private String nickNameFuzzy;

	/**
	 * 邮箱——模糊匹配（扩展）
	 */
	private String emailFuzzy;

	/**
	 * 密码——模糊匹配（扩展）
	 */
	private String passwordFuzzy;

	/**
	 * 出生日期——模糊匹配（扩展）
	 */
	private String birthdayFuzzy;

	/**
	 * 学校——模糊匹配（扩展）
	 */
	private String schoolFuzzy;

	/**
	 * 个人简介——模糊匹配（扩展）
	 */
	private String personIntroductionFuzzy;

	/**
	 * 注册时间——起始点（扩展）
	 */
	private String joinTimeStart;

	/**
	 * 注册时间——结束点（扩展）
	 */
	private String joinTimeEnd;

	/**
	 * 最近登录时间——起始点（扩展）
	 */
	private String lastLoginTimeStart;

	/**
	 * 最近登录时间——结束点（扩展）
	 */
	private String lastLoginTimeEnd;

	/**
	 * 最近登录ip——模糊匹配（扩展）
	 */
	private String lastLoginIpFuzzy;

	/**
	 * 0:禁用1:正常——模糊匹配（扩展）
	 */
	private String statusFuzzy;

	/**
	 * 空间公告——模糊匹配（扩展）
	 */
	private String noticeInfoFuzzy;

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchool() {
		return this.school;
	}

	public void setPersonIntroduction(String personIntroduction) {
		this.personIntroduction = personIntroduction;
	}

	public String getPersonIntroduction() {
		return this.personIntroduction;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Date getJoinTime() {
		return this.joinTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setNoticeInfo(String noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public String getNoticeInfo() {
		return this.noticeInfo;
	}

	public void setTotalCoinCount(Integer totalCoinCount) {
		this.totalCoinCount = totalCoinCount;
	}

	public Integer getTotalCoinCount() {
		return this.totalCoinCount;
	}

	public void setCurrentCoinCount(Integer currentCoinCount) {
		this.currentCoinCount = currentCoinCount;
	}

	public Integer getCurrentCoinCount() {
		return this.currentCoinCount;
	}

	public void setTheme(Integer theme) {
		this.theme = theme;
	}

	public Integer getTheme() {
		return this.theme;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getNickNameFuzzy() {
		return this.nickNameFuzzy;
	}

	public void setEmailFuzzy(String emailFuzzy) {
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy() {
		return this.emailFuzzy;
	}

	public void setPasswordFuzzy(String passwordFuzzy) {
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPasswordFuzzy() {
		return this.passwordFuzzy;
	}

	public void setBirthdayFuzzy(String birthdayFuzzy) {
		this.birthdayFuzzy = birthdayFuzzy;
	}

	public String getBirthdayFuzzy() {
		return this.birthdayFuzzy;
	}

	public void setSchoolFuzzy(String schoolFuzzy) {
		this.schoolFuzzy = schoolFuzzy;
	}

	public String getSchoolFuzzy() {
		return this.schoolFuzzy;
	}

	public void setPersonIntroductionFuzzy(String personIntroductionFuzzy) {
		this.personIntroductionFuzzy = personIntroductionFuzzy;
	}

	public String getPersonIntroductionFuzzy() {
		return this.personIntroductionFuzzy;
	}

	public void setJoinTimeStart(String joinTimeStart) {
		this.joinTimeStart = joinTimeStart;
	}

	public String getJoinTimeStart() {
		return this.joinTimeStart;
	}

	public void setJoinTimeEnd(String joinTimeEnd) {
		this.joinTimeEnd = joinTimeEnd;
	}

	public String getJoinTimeEnd() {
		return this.joinTimeEnd;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeStart() {
		return this.lastLoginTimeStart;
	}

	public void setLastLoginTimeEnd(String lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginTimeEnd() {
		return this.lastLoginTimeEnd;
	}

	public void setLastLoginIpFuzzy(String lastLoginIpFuzzy) {
		this.lastLoginIpFuzzy = lastLoginIpFuzzy;
	}

	public String getLastLoginIpFuzzy() {
		return this.lastLoginIpFuzzy;
	}

	public void setStatusFuzzy(String statusFuzzy) {
		this.statusFuzzy = statusFuzzy;
	}

	public String getStatusFuzzy() {
		return this.statusFuzzy;
	}

	public void setNoticeInfoFuzzy(String noticeInfoFuzzy) {
		this.noticeInfoFuzzy = noticeInfoFuzzy;
	}

	public String getNoticeInfoFuzzy() {
		return this.noticeInfoFuzzy;
	}

}