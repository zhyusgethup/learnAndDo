package gener.tempP.template.temp;

import java.util.List;

public class SimpleTemplate implements Template{
	private String word;
	public static final String DEFAULT_VAR_SIGN = "$[23sd{}";
	private String varSign;
	private List<Param> params;
}
