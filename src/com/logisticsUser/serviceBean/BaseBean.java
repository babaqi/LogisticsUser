package com.logisticsUser.serviceBean;

/**
 * 
 * @author muz
 *
 */
public class BaseBean {

	/**服务器返回的code**/
	private String retcode;

	/**服务器返回的信息**/
	private String retmsg;

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

}
