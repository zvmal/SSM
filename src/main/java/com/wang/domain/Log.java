package com.wang.domain;

import java.util.Date;

public class Log {
	/**
     * ��־id
     */
    private Integer id;

    /**
     * ��ǰ������id
     */
    private String loginAccount;

    /**
     * ��ǰ������ip
     */
    private String loginIp;

    /**
     * �������������
     */
    private String actionUrl;

    /**
     * ִ�е�ģ��
     */
    private String module;

    /**
     * ִ�еķ���
     */
    private String method;

    /**
     * ִ�в���ʱ��
     */
    private Long actionTime;

    /**
     * ����
     */
    private String description;

    /**
     * ִ�е�ʱ��
     */
    private Date gmtCreate;

    /**
     * �ò���״̬��1��ʾ�ɹ���-1��ʾʧ�ܣ�
     */
    private Short state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getActionTime() {
		return actionTime;
	}

	public void setActionTime(Long actionTime) {
		this.actionTime = actionTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", loginAccount=" + loginAccount + ", loginIp=" + loginIp + ", actionUrl=" + actionUrl
				+ ", module=" + module + ", method=" + method + ", actionTime=" + actionTime + ", description="
				+ description + ", gmtCreate=" + gmtCreate + ", state=" + state + "]";
	}
	  
}
