package utils;

public enum StaffType {
  
    NORMAL_STAFF("Normal Staff"), ADMIN_STAFF("Administration Staff");
    private String value;

    private StaffType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
    	return this.value;
    }
}
