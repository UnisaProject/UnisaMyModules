package Srslf05h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srslf05sDisplaySmsStaffInfoTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srslf05h.Servlet.Srslf05sDisplaySmsStaffInfoServlet";
    public String getServletName()     {return ServletName; }
 
    public Srslf05sDisplaySmsStaffInfoTran ()
   {
      super ();
      String myName = "Srslf05sDisplaySmsStaffInfo()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srslf05sDisplaySmsStaffInfoTran (serverDialog sysObj,
                                  Srslf05sDisplaySmsStaffInfoInput viewIn,
                                  Srslf05sDisplaySmsStaffInfoOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srslf05sDisplaySmsStaffInfoTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srslf05sDisplaySmsStaffInfoTran (serverDialog sysObj,
                                  Srslf05sDisplaySmsStaffInfoInput viewIn,
                                  Srslf05sDisplaySmsStaffInfoOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srslf05sDisplaySmsStaffInfoTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srslf05sDisplaySmsStaffInfoInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srslf05sDisplaySmsStaffInfoOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srslf05h.Srslf05sDisplaySmsStaffInfoInput")
      checkInViewValue ((Srslf05sDisplaySmsStaffInfoInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srslf05sDisplaySmsStaffInfoInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srslf05h.Srslf05sDisplaySmsStaffInfoOutput")
      checkOutViewValue ((Srslf05sDisplaySmsStaffInfoOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srslf05sDisplaySmsStaffInfoOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srslf05sDisplaySmsStaffInfoInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srslf05sDisplaySmsStaffInfoOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
