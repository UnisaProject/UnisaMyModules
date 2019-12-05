package Saacq01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Saacq01sAssessmentCriteriaMntTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Saacq01h.Servlet.Saacq01sAssessmentCriteriaMntServlet";
    public String getServletName()     {return ServletName; }
 
    public Saacq01sAssessmentCriteriaMntTran ()
   {
      super ();
      String myName = "Saacq01sAssessmentCriteriaMnt()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Saacq01sAssessmentCriteriaMntTran (serverDialog sysObj,
                                  Saacq01sAssessmentCriteriaMntInput viewIn,
                                  Saacq01sAssessmentCriteriaMntOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Saacq01sAssessmentCriteriaMntTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Saacq01sAssessmentCriteriaMntTran (serverDialog sysObj,
                                  Saacq01sAssessmentCriteriaMntInput viewIn,
                                  Saacq01sAssessmentCriteriaMntOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Saacq01sAssessmentCriteriaMntTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Saacq01sAssessmentCriteriaMntInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Saacq01sAssessmentCriteriaMntOutput x)
       throws PropertyVetoException
  {
    checkOutViewValue (x);
    super.setOutView (x);
  }
 
  //--------------------------------------------------
  // Process vetoable change requests for input properties with permitted values
 
  public synchronized void InViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Saacq01h.Saacq01sAssessmentCriteriaMntInput")
      checkInViewValue ((Saacq01sAssessmentCriteriaMntInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Saacq01sAssessmentCriteriaMntInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Saacq01h.Saacq01sAssessmentCriteriaMntOutput")
      checkOutViewValue ((Saacq01sAssessmentCriteriaMntOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Saacq01sAssessmentCriteriaMntOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Saacq01sAssessmentCriteriaMntInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Saacq01sAssessmentCriteriaMntOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
