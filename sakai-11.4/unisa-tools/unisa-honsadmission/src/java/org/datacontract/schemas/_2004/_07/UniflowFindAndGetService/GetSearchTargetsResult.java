/**
 * GetSearchTargetsResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.UniflowFindAndGetService;

public class GetSearchTargetsResult  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus;

    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] searchTargets;

    public GetSearchTargetsResult() {
    }

    public GetSearchTargetsResult(
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus,
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] searchTargets) {
           this.resultStatus = resultStatus;
           this.searchTargets = searchTargets;
    }


    /**
     * Gets the resultStatus value for this GetSearchTargetsResult.
     * 
     * @return resultStatus
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus getResultStatus() {
        return resultStatus;
    }


    /**
     * Sets the resultStatus value for this GetSearchTargetsResult.
     * 
     * @param resultStatus
     */
    public void setResultStatus(org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }


    /**
     * Gets the searchTargets value for this GetSearchTargetsResult.
     * 
     * @return searchTargets
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] getSearchTargets() {
        return searchTargets;
    }


    /**
     * Sets the searchTargets value for this GetSearchTargetsResult.
     * 
     * @param searchTargets
     */
    public void setSearchTargets(org.datacontract.schemas._2004._07.UniflowFindAndGetService.DocumentLocation[] searchTargets) {
        this.searchTargets = searchTargets;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSearchTargetsResult)) return false;
        GetSearchTargetsResult other = (GetSearchTargetsResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resultStatus==null && other.getResultStatus()==null) || 
             (this.resultStatus!=null &&
              this.resultStatus.equals(other.getResultStatus()))) &&
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
        if (getResultStatus() != null) {
            _hashCode += getResultStatus().hashCode();
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
        new org.apache.axis.description.TypeDesc(GetSearchTargetsResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "GetSearchTargetsResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "ResultStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "ResultStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
