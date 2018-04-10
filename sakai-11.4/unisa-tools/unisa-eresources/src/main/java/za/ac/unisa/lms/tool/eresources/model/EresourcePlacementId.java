package za.ac.unisa.lms.tool.eresources.model;



	import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.appfuse.model.BaseObject;
	 
	@Embeddable
	public class EresourcePlacementId  extends BaseObject implements java.io.Serializable {
	 
		/**
		 * 
		 */
		private static final long serialVersionUID = 3826235461328773070L;
		
		private Placement placement;
	    private Eresource eresource;
	 
	    @ManyToOne
		public Placement getPlacement() {
			return placement;
		}
	 
		public void setPlacement(Placement placement) {
			this.placement = placement;
		}
	 
		@ManyToOne
		public Eresource getEresource() {
			return eresource;
		}
	 
		public void setEresource(Eresource eresource) {
			this.eresource = eresource;
		}
	 
		public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	 
	        EresourcePlacementId that = (EresourcePlacementId) o;
	 
	        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
	        if (eresource != null ? !eresource.equals(that.eresource) : that.eresource != null)
	            return false;
	 
	        return true;
	    }
	 
	    public int hashCode() {
	        int result;
	        result = (placement != null ? placement.hashCode() : 0);
	        result = 31 * result + (eresource != null ? eresource.hashCode() : 0);
	        return result;
	    }

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "EresourcePlacementId [placement=" + placement
					+ ", eresource=" + eresource + "]";
		}

}
