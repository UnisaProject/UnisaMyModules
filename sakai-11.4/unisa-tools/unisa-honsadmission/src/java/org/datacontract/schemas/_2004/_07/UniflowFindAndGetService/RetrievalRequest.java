/**
 * RetrievalRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.UniflowFindAndGetService;

public class RetrievalRequest  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails authenticationDetails;

    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentID documentID;

    private java.lang.Boolean mandDOnly;

    public RetrievalRequest() {
    }

    public RetrievalRequest(
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails authenticationDetails,
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentID documentID,
           java.lang.Boolean mandDOnly) {
           this.authenticationDetails = authenticationDetails;
           this.documentID = documentID;
           this.mandDOnly = mandDOnly;
    }


    /**
     * Gets the authenticationDetails value for this RetrievalRequest.
     * 
     * @return authenticationDetails
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails getAuthenticationDetails() {
        return authenticationDetails;
    }


    /**
     * Sets the authenticationDetails value for this RetrievalRequest.
     * 
     * @param authenticationDetails
     */
    public void setAuthenticationDetails(org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails authenticationDetails) {
        this.authenticationDetails = authenticationDetails;
    }


    /**
     * Gets the documentID value for this RetrievalRequest.
     * 
     * @return documentID
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentID getDocumentID() {
        return documentID;
    }


    /**
     * Sets the documentID value for this RetrievalRequest.
     * 
     * @param documentID
     */
    public void setDocumentID(org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentID documentID) {
        this.documentID = documentID;
    }


    /**
     * Gets the mandDOnly value for this RetrievalRequest.
     * 
     * @return mandDOnly
     */
    public java.lang.Boolean getMandDOnly() {
        return mandDOnly;
    }


    /**
     * Sets the mandDOnly value for this RetrievalRequest.
     * 
     * @param mandDOnly
     */
    public void setMandDOnly(java.lang.Boolean mandDOnly) {
        this.mandDOnly = mandDOnly;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrievalRequest)) return false;
        RetrievalRequest other = (RetrievalRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authenticationDetails==null && other.getAuthenticationDetails()==null) || 
             (this.authenticationDetails!=null &&
              this.authenticationDetails.equals(other.getAuthenticationDetails()))) &&
            ((this.documentID==null && other.getDocumentID()==null) || 
             (this.documentID!=null &&
              this.documentID.equals(other.getDocumentID()))) &&
            ((this.mandDOnly==null && other.getMandDOnly()==null) || 
             (this.mandDOnly!=null &&
              this.mandDOnly.equals(other.getMandDOnly())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAuthenticationDetails() != null) {
            _hashCode += getAuthenticationDetails().hashCode();
        }
        if (getDocumentID() != null) {
            _hashCode += getDocumentID().hashCode();
        }
        if (getMandDOnly() != null) {
            _hashCode += getMandDOnly().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrievalRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "RetrievalRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "AuthenticationDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "AuthenticationDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "DocumentID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "DocumentID"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandDOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "MandDOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
