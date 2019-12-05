package Sfstj09h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Sfstj09sDspStudentAccountTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Sfstj09h.Servlet.Sfstj09sDspStudentAccountServlet";
    public String getServletName()     {return ServletName; }
 
    public Sfstj09sDspStudentAccountTran ()
   {
      super ();
      String myName = "Sfstj09sDspStudentAccount()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Sfstj09sDspStudentAccountTran (serverDialog sysObj,
                                  Sfstj09sDspStudentAccountInput viewIn,
                                  Sfstj09sDspStudentAccountOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfstj09sDspStudentAccountTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Sfstj09sDspStudentAccountTran (serverDialog sysObj,
                                  Sfstj09sDspStudentAccountInput viewIn,
                                  Sfstj09sDspStudentAccountOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sfstj09sDspStudentAccountTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Sfstj09sDspStudentAccountInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Sfstj09sDspStudentAccountOutput x)
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
    if (e.getNewValue().getClass().getName() == "Sfstj09h.Sfstj09sDspStudentAccountInput")
      checkInViewValue ((Sfstj09sDspStudentAccountInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Sfstj09sDspStudentAccountInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Sfstj09h.Sfstj09sDspStudentAccountOutput")
      checkOutViewValue ((Sfstj09sDspStudentAccountOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Sfstj09sDspStudentAccountOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Sfstj09sDspStudentAccountInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Sfstj09sDspStudentAccountOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
