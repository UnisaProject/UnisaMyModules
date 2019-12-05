package Sfstj10h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Sfstj10sLstStudentAccountTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Sfstj10h.Servlet.Sfstj10sLstStudentAccountServlet";
    public String getServletName()     {return ServletName; }
 
    public Sfstj10sLstStudentAccountTran ()
   {
      super ();
      String myName = "Sfstj10sLstStudentAccount()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Sfstj10sLstStudentAccountTran (serverDialog sysObj,
                                  Sfstj10sLstStudentAccountInput viewIn,
                                  Sfstj10sLstStudentAccountOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfstj10sLstStudentAccountTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Sfstj10sLstStudentAccountTran (serverDialog sysObj,
                                  Sfstj10sLstStudentAccountInput viewIn,
                                  Sfstj10sLstStudentAccountOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfstj10sLstStudentAccountTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Sfstj10sLstStudentAccountInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Sfstj10sLstStudentAccountOutput x)
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
    if (e.getNewValue().getClass().getName() == "Sfstj10h.Sfstj10sLstStudentAccountInput")
      checkInViewValue ((Sfstj10sLstStudentAccountInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Sfstj10sLstStudentAccountInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Sfstj10h.Sfstj10sLstStudentAccountOutput")
      checkOutViewValue ((Sfstj10sLstStudentAccountOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Sfstj10sLstStudentAccountOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Sfstj10sLstStudentAccountInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Sfstj10sLstStudentAccountOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
