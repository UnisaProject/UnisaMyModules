/**
 * RetrievalResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.UniflowFindAndGetService;

public class RetrievalResult  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.Document document;

    private org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus;

    public RetrievalResult() {
    }

    public RetrievalResult(
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.Document document,
           org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus) {
           this.document = document;
           this.resultStatus = resultStatus;
    }


    /**
     * Gets the document value for this RetrievalResult.
     * 
     * @return document
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.Document getDocument() {
        return document;
    }


    /**
     * Sets the document value for this RetrievalResult.
     * 
     * @param document
     */
    public void setDocument(org.datacontract.schemas._2004._07.UniflowFindAndGetService.Document document) {
        this.document = document;
    }


    /**
     * Gets the resultStatus value for this RetrievalResult.
     * 
     * @return resultStatus
     */
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus getResultStatus() {
        return resultStatus;
    }


    /**
     * Sets the resultStatus value for this RetrievalResult.
     * 
     * @param resultStatus
     */
    public void setResultStatus(org.datacontract.schemas._2004._07.UniflowFindAndGetService.ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrievalResult)) return false;
        RetrievalResult other = (RetrievalResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.document==null && other.getDocument()==null) || 
             (this.document!=null &&
              this.document.equals(other.getDocument()))) &&
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
        if (getDocument() != null) {
            _hashCode += getDocument().hashCode();
        }
        if (getResultStatus() != null) {
            _hashCode += getResultStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrievalResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "RetrievalResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("document");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "Document"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/UniflowFindAndGetService", "Document"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
