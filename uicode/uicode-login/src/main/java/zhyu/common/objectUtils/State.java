package zhyu.common.objectUtils;

public enum State {
	success("0000", "操作成功！"),
	failure("0001", "操作失败！"),
	disable("0008", "接口不存在或已停用！"),
	app_version_disable("0002", "app版本不是最新的，不能登录！"),
	disable_authorize("0009", "没有接口使用权或使用权已过期！"),
	ticket_failure("1000", "用户信息失效，为了账户安全，请重新登录！"),
	reject(	"1009", "权限拒绝！"),
	miss_service_code("0011", "接口编码不能为空！"),
	miss_security_code("0012", "接口验证码不能为空！"),
	miss_argument("0021", "参数错误或不完整！"),
	miss_terminal("0022", "终端类型不能为空！"),
	miss_terminal_os_type("0025", "终端操作系统类型不能为空！"),
	miss_intf_uri("0023", "该接口对应的业务未实现！"),
	miss_authorize("0024", "无法获取授权信息！"),
	argument_failure("0031", "参数异常，传输过程中可能被改动！"),
	unknown("UE01", "接口未知异常！"),
	noresult("UE02","响应码未知！"),
	/*<<<<<<<<<<<<<<<<<<<业务用CODE<<<<<<<<<<<<<<<<<<<*/
	login_failure("01","用户登陆失败"),
	/*>>>>>>>>>>>>>>>>>>>业务用CODE>>>>>>>>>>>>>>>>>>>*/
	intf_exception("UE03", "接口异常！");
	

	private String code;
	private String msg;

	private State(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "{code:\"" + code + "\", msg:\"" + msg + "\"}";
	}
}
