package za.ac.unisa.lms.tools.cronjobs.forms.model;

import java.util.List;
import java.util.Set;
import za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl.ProvinceImpl;

public class Province {
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
	
	public String provinceListAsString(List provinceCodes)throws Exception {
        ProvinceImpl provinceImpl=new ProvinceImpl();
        return provinceImpl.provinceListAsString(provinceCodes);
   }
	public Set getProvinceCodeList() throws Exception {
		            ProvinceImpl provinceImpl=new ProvinceImpl();
                    return provinceImpl.getProvinceCodeList();
   }
}
