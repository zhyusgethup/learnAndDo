package gener.projectHero;

import java.util.Arrays;
import java.util.List;

import javax.security.auth.x500.X500Principal;

public class Vo {
	private String name;
	private String message;
	private Integer id;
	private List<Param> params;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id + MODEL_ID;
	}



	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public static Integer getModelId() {
		return MODEL_ID;
	}

	public static final Integer MODEL_ID = 120000;
	
	@Override
	public String toString() {
		final StringBuilder para = new StringBuilder();
		params.forEach( x -> para.append(x));
		return "Vo [name=" + name + ", message=" + message + ", id=" + id + "params = " + para + "]";
	}

	public static class Param {
		private String name;
		private String type;
		public Param(String name, String type) {
			super();
			this.name = name;
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		@Override
		public String toString() {
			return "Param [name=" + name + ", type=" + type + "]";
		}
	}
}
