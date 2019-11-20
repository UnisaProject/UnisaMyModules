package Sfrrf03h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Sfrrf03sMntOnlineCcPaymentsTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Sfrrf03h.Servlet.Sfrrf03sMntOnlineCcPaymentsServlet";
    public String getServletName()     {return ServletName; }
 
    public Sfrrf03sMntOnlineCcPaymentsTran ()
   {
      super ();
      String myName = "Sfrrf03sMntOnlineCcPayments()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Sfrrf03sMntOnlineCcPaymentsTran (serverDialog sysObj,
                                  Sfrrf03sMntOnlineCcPaymentsInput viewIn,
                                  Sfrrf03sMntOnlineCcPaymentsOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfrrf03sMntOnlineCcPaymentsTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Sfrrf03sMntOnlineCcPaymentsTran (serverDialog sysObj,
                                  Sfrrf03sMntOnlineCcPaymentsInput viewIn,
                                  Sfrrf03sMntOnlineCcPaymentsOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfrrf03sMntOnlineCcPaymentsTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Sfrrf03sMntOnlineCcPaymentsInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Sfrrf03sMntOnlineCcPaymentsOutput x)
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
    if (e.getNewValue().getClass().getName() == "Sfrrf03h.Sfrrf03sMntOnlineCcPaymentsInput")
      checkInViewValue ((Sfrrf03sMntOnlineCcPaymentsInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Sfrrf03sMntOnlineCcPaymentsInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Sfrrf03h.Sfrrf03sMntOnlineCcPaymentsOutput")
      checkOutViewValue ((Sfrrf03sMntOnlineCcPaymentsOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Sfrrf03sMntOnlineCcPaymentsOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Sfrrf03sMntOnlineCcPaymentsInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Sfrrf03sMntOnlineCcPaymentsOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
