/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.appfuse.model.BaseObject;

/**
 * @author TMasibm
 *
 */
@Entity
@Table(name = "LIBALPHABET")

public class Alphabet extends BaseObject implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long ID;
	private char alphabet;
	
	public Alphabet() {
		
	}
	

	/**
	 * @param id
	 * @param alphabet
	 */
	public Alphabet(long id, char alphabet) {
		this.ID = id;
		this.alphabet = alphabet;
	}
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public long getId() {
		return ID;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.ID = id;
	}

	/**
	 * @return the alphabet
	 */

	@Column(name = "Alphabet", unique = true)
	public char getAlphabet() {
		return alphabet;
	}

	/**
	 * @param alphabet the alphabet to set
	 */
	public void setAlphabet(char alphabet) {
		this.alphabet = alphabet;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		result = prime * result + alphabet;
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Alphabet)) {
			return false;
		}
		Alphabet other = (Alphabet) obj;
		if (ID != other.ID) {
			return false;
		}
		if (alphabet != other.alphabet) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alphabet [ID=" + ID + ", alphabet=" + alphabet + "]";
	}


	
	
}
