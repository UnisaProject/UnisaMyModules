/**
 * SearchRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.UniflowFindAndGetService;

public class SearchRequest  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails authenticationDetails;

    private java.lang.Boolean mandDOnly;

    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchField[] searchFields;

    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] searchTargets;

    public SearchRequest() {
    }

    public SearchRequest(
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails authenticationDetails,
           java.lang.Boolean mandDOnly,
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchField[] searchFields,
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] searchTargets) {
           this.authenticationDetails = authenticationDetails;
           this.mandDOnly = mandDOnly;
           this.searchFields = searchFields;
           this.searchTargets = searchTargets;
    }


    /**
     * Gets the authenticationDetails value for this SearchRequest.
     * 
     * @return authenticationDetails
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails getAuthenticationDetails() {
        return authenticationDetails;
    }


    /**
     * Sets the authenticationDetails value for this SearchRequest.
     * 
     * @param authenticationDetails
     */
    public void setAuthenticationDetails(org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails authenticationDetails) {
        this.authenticationDetails = authenticationDetails;
    }


    /**
     * Gets the mandDOnly value for this SearchRequest.
     * 
     * @return mandDOnly
     */
    public java.lang.Boolean getMandDOnly() {
        return mandDOnly;
    }


    /**
     * Sets the mandDOnly value for this SearchRequest.
     * 
     * @param mandDOnly
     */
    public void setMandDOnly(java.lang.Boolean mandDOnly) {
        this.mandDOnly = mandDOnly;
    }


    /**
     * Gets the searchFields value for this SearchRequest.
     * 
     * @return searchFields
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchField[] getSearchFields() {
        return searchFields;
    }


    /**
     * Sets the searchFields value for this SearchRequest.
     * 
     * @param searchFields
     */
    public void setSearchFields(org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchField[] searchFields) {
        this.searchFields = searchFields;
    }


    /**
     * Gets the searchTargets value for this SearchRequest.
     * 
     * @return searchTargets
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] getSearchTargets() {
        return searchTargets;
    }


    /**
     * Sets the searchTargets value for this SearchRequest.
     * 
     * @param searchTargets
     */
    public void setSearchTargets(org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] searchTargets) {
        this.searchTargets = searchTargets;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchRequest)) return false;
        SearchRequest other = (SearchRequest) obj;
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
            ((this.mandDOnly==null && other.getMandDOnly()==null) || 
             (this.mandDOnly!=null &&
              this.mandDOnly.equals(other.getMandDOnly()))) &&
            ((this.searchFields==null && other.getSearchFields()==null) || 
             (this.searchFields!=null &&
              java.util.Arrays.equals(this.searchFields, other.getSearchFields()))) &&
            ((this.searchTargets==null && other.getSearchTargets()==null) || 
             (this.searchTargets!=null &&
              java.util.Arrays.equals(this.searchTargets, other.getSearchTargets())));
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
        if (getMandDOnly() != null) {
            _hashCode += getMandDOnly().hashCode();
        }
        if (getSearchFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchTargets() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchTargets());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchTargets(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "SearchRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "AuthenticationDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "AuthenticationDetails"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "SearchFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "SearchField"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "SearchField"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchTargets");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "SearchTargets"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "DocumentLocation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "DocumentLocation"));
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
