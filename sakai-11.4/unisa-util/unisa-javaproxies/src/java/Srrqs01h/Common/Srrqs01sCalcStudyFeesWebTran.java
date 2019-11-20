package Srrqs01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srrqs01sCalcStudyFeesWebTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srrqs01h.Servlet.Srrqs01sCalcStudyFeesWebServlet";
    public String getServletName()     {return ServletName; }
 
    public Srrqs01sCalcStudyFeesWebTran ()
   {
      super ();
      String myName = "Srrqs01sCalcStudyFeesWeb()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srrqs01sCalcStudyFeesWebTran (serverDialog sysObj,
                                  Srrqs01sCalcStudyFeesWebInput viewIn,
                                  Srrqs01sCalcStudyFeesWebOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srrqs01sCalcStudyFeesWebTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srrqs01sCalcStudyFeesWebTran (serverDialog sysObj,
                                  Srrqs01sCalcStudyFeesWebInput viewIn,
                                  Srrqs01sCalcStudyFeesWebOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srrqs01sCalcStudyFeesWebTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srrqs01sCalcStudyFeesWebInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srrqs01sCalcStudyFeesWebOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srrqs01h.Srrqs01sCalcStudyFeesWebInput")
      checkInViewValue ((Srrqs01sCalcStudyFeesWebInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srrqs01sCalcStudyFeesWebInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srrqs01h.Srrqs01sCalcStudyFeesWebOutput")
      checkOutViewValue ((Srrqs01sCalcStudyFeesWebOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srrqs01sCalcStudyFeesWebOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srrqs01sCalcStudyFeesWebInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srrqs01sCalcStudyFeesWebOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
