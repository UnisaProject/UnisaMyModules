package za.ac.unisa.lms.tools.finalmarkconcession.forms;

public class GradeBookObjectRecord {
	
	private int id;
	private String name;
	private int gradeBookId;
	private float pointsPossible;
	
	public float getPointsPossible() {
		return pointsPossible;
	}
	public void setPointsPossible(float pointsPossible) {
		this.pointsPossible = pointsPossible;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGradeBookId() {
		return gradeBookId;
	}
	public void setGradeBookId(int gradeBookId) {
		this.gradeBookId = gradeBookId;
	}

}
