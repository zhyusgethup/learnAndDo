package gener.tempP.template.temp;

public class Param {
	private String name;
	private ParamChecker checker;
	private String value;
	private Template template;
	private int pos;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ParamChecker getChecker() {
		return checker;
	}
	public void setChecker(ParamChecker checker) {
		this.checker = checker;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) throws CheckException {
		if(null != checker && checker.check(value))
			this.value = value;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	@Override
	public String toString() {
		return "Param [name=" + name + ", checker=" + checker + ", value=" + value + ", template=" + template + ", pos="
				+ pos + "]";
	}
	public Param(String name, ParamChecker checker, Template template, int pos) {
		super();
		this.name = name;
		this.checker = checker;
		this.template = template;
		this.pos = pos;
	}
	public Param(String name, Template template, int pos) {
		super();
		this.name = name;
		this.template = template;
		this.pos = pos;
	}
	
	
}
