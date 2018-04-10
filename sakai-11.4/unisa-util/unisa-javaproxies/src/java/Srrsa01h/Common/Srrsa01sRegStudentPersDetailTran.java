package Srrsa01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srrsa01sRegStudentPersDetailTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srrsa01h.Servlet.Srrsa01sRegStudentPersDetailServlet";
    public String getServletName()     {return ServletName; }
 
    public Srrsa01sRegStudentPersDetailTran ()
   {
      super ();
      String myName = "Srrsa01sRegStudentPersDetail()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srrsa01sRegStudentPersDetailTran (serverDialog sysObj,
                                  Srrsa01sRegStudentPersDetailInput viewIn,
                                  Srrsa01sRegStudentPersDetailOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srrsa01sRegStudentPersDetailTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srrsa01sRegStudentPersDetailTran (serverDialog sysObj,
                                  Srrsa01sRegStudentPersDetailInput viewIn,
                                  Srrsa01sRegStudentPersDetailOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srrsa01sRegStudentPersDetailTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srrsa01sRegStudentPersDetailInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srrsa01sRegStudentPersDetailOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srrsa01h.Srrsa01sRegStudentPersDetailInput")
      checkInViewValue ((Srrsa01sRegStudentPersDetailInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srrsa01sRegStudentPersDetailInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srrsa01h.Srrsa01sRegStudentPersDetailOutput")
      checkOutViewValue ((Srrsa01sRegStudentPersDetailOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srrsa01sRegStudentPersDetailOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srrsa01sRegStudentPersDetailInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srrsa01sRegStudentPersDetailOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
