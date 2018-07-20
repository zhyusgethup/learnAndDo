package zhyu.common.objectUtils;

public class BusiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String exceptionCd = "";
	private String exceptionMsg = "";
	
	public BusiException(String cd, String msg) {
		this.exceptionCd = cd;
		this.exceptionMsg = msg;
	}

	public String getExceptionCd() {
		return exceptionCd;
	}

	public void setExceptionCd(String exceptionCd) {
		this.exceptionCd = exceptionCd;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	
}
