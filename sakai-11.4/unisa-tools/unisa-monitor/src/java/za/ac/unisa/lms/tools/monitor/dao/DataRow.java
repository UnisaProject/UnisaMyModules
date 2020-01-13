package za.ac.unisa.lms.tools.monitor.dao;

import java.util.Vector;

public class DataRow {

	private Vector<?> columns;
	private Vector<?> values;
	
	public Vector<?> getColumns() {
		return columns;
	}
	public void setColumns(Vector<?> columns) {
		this.columns = columns;
	}
	public Vector<?> getValues() {
		return values;
	}
	public void setValues(Vector<?> values) {
		this.values = values;
	}
	
}
