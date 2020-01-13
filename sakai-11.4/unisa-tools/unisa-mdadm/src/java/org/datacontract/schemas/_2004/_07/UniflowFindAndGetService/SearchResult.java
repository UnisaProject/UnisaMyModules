/**
 * SearchResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.UniflowFindAndGetService;

public class SearchResult  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.RecordSet[] recordSets;

    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus;

    public SearchResult() {
    }

    public SearchResult(
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.RecordSet[] recordSets,
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus) {
           this.recordSets = recordSets;
           this.resultStatus = resultStatus;
    }


    /**
     * Gets the recordSets value for this SearchResult.
     * 
     * @return recordSets
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.RecordSet[] getRecordSets() {
        return recordSets;
    }


    /**
     * Sets the recordSets value for this SearchResult.
     * 
     * @param recordSets
     */
    public void setRecordSets(org.datacontract.schemas._2004._07.UniflowFindAndGetService.RecordSet[] recordSets) {
        this.recordSets = recordSets;
    }


    /**
     * Gets the resultStatus value for this SearchResult.
     * 
     * @return resultStatus
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus getResultStatus() {
        return resultStatus;
    }


    /**
     * Sets the resultStatus value for this SearchResult.
     * 
     * @param resultStatus
     */
    public void setResultStatus(org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchResult)) return false;
        SearchResult other = (SearchResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recordSets==null && other.getRecordSets()==null) || 
             (this.recordSets!=null &&
              java.util.Arrays.equals(this.recordSets, other.getRecordSets()))) &&
            ((this.resultStatus==null && other.getResultStatus()==null) || 
             (this.resultStatus!=null &&
              this.resultStatus.equals(other.getResultStatus())));
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
        if (getRecordSets() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRecordSets());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRecordSets(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResultStatus() != null) {
            _hashCode += getResultStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "SearchResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordSets");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "RecordSets"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "RecordSet"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "RecordSet"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "ResultStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "ResultStatus"));
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
