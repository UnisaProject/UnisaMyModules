package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ProvinceImpl;
import java.util.List;
public class Province extends ProvinceImpl {
	private Short code;
	private String description;
	private String in_use;
	public Short getCode() {
		return code;
	}
	public void setCode(Short code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIn_use() {
		return in_use;
	}
	public void setIn_use(String inUse) {
		in_use = inUse;
	}
	
}
