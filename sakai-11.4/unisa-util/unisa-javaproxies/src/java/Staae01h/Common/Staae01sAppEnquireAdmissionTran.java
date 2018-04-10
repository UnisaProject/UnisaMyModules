package Staae01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Staae01sAppEnquireAdmissionTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Staae01h.Servlet.Staae01sAppEnquireAdmissionServlet";
    public String getServletName()     {return ServletName; }
 
    public Staae01sAppEnquireAdmissionTran ()
   {
      super ();
      String myName = "Staae01sAppEnquireAdmission()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Staae01sAppEnquireAdmissionTran (serverDialog sysObj,
                                  Staae01sAppEnquireAdmissionInput viewIn,
                                  Staae01sAppEnquireAdmissionOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Staae01sAppEnquireAdmissionTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Staae01sAppEnquireAdmissionTran (serverDialog sysObj,
                                  Staae01sAppEnquireAdmissionInput viewIn,
                                  Staae01sAppEnquireAdmissionOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Staae01sAppEnquireAdmissionTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Staae01sAppEnquireAdmissionInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Staae01sAppEnquireAdmissionOutput x)
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
    if (e.getNewValue().getClass().getName() == "Staae01h.Staae01sAppEnquireAdmissionInput")
      checkInViewValue ((Staae01sAppEnquireAdmissionInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Staae01sAppEnquireAdmissionInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Staae01h.Staae01sAppEnquireAdmissionOutput")
      checkOutViewValue ((Staae01sAppEnquireAdmissionOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Staae01sAppEnquireAdmissionOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Staae01sAppEnquireAdmissionInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Staae01sAppEnquireAdmissionOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
