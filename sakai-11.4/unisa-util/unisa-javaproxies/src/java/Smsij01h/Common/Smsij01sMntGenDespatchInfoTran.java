package Smsij01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Smsij01sMntGenDespatchInfoTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Smsij01h.Servlet.Smsij01sMntGenDespatchInfoServlet";
    public String getServletName()     {return ServletName; }
 
    public Smsij01sMntGenDespatchInfoTran ()
   {
      super ();
      String myName = "Smsij01sMntGenDespatchInfo()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Smsij01sMntGenDespatchInfoTran (serverDialog sysObj,
                                  Smsij01sMntGenDespatchInfoInput viewIn,
                                  Smsij01sMntGenDespatchInfoOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Smsij01sMntGenDespatchInfoTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Smsij01sMntGenDespatchInfoTran (serverDialog sysObj,
                                  Smsij01sMntGenDespatchInfoInput viewIn,
                                  Smsij01sMntGenDespatchInfoOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Smsij01sMntGenDespatchInfoTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Smsij01sMntGenDespatchInfoInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Smsij01sMntGenDespatchInfoOutput x)
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
    if (e.getNewValue().getClass().getName() == "Smsij01h.Smsij01sMntGenDespatchInfoInput")
      checkInViewValue ((Smsij01sMntGenDespatchInfoInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Smsij01sMntGenDespatchInfoInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Smsij01h.Smsij01sMntGenDespatchInfoOutput")
      checkOutViewValue ((Smsij01sMntGenDespatchInfoOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Smsij01sMntGenDespatchInfoOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Smsij01sMntGenDespatchInfoInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Smsij01sMntGenDespatchInfoOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
