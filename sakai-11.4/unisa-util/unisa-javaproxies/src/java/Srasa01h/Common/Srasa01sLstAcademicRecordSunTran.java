package Srasa01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srasa01sLstAcademicRecordSunTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srasa01h.Servlet.Srasa01sLstAcademicRecordSunServlet";
    public String getServletName()     {return ServletName; }
 
    public Srasa01sLstAcademicRecordSunTran ()
   {
      super ();
      String myName = "Srasa01sLstAcademicRecordSun()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srasa01sLstAcademicRecordSunTran (serverDialog sysObj,
                                  Srasa01sLstAcademicRecordSunInput viewIn,
                                  Srasa01sLstAcademicRecordSunOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srasa01sLstAcademicRecordSunTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srasa01sLstAcademicRecordSunTran (serverDialog sysObj,
                                  Srasa01sLstAcademicRecordSunInput viewIn,
                                  Srasa01sLstAcademicRecordSunOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srasa01sLstAcademicRecordSunTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srasa01sLstAcademicRecordSunInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srasa01sLstAcademicRecordSunOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srasa01h.Srasa01sLstAcademicRecordSunInput")
      checkInViewValue ((Srasa01sLstAcademicRecordSunInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srasa01sLstAcademicRecordSunInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srasa01h.Srasa01sLstAcademicRecordSunOutput")
      checkOutViewValue ((Srasa01sLstAcademicRecordSunOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srasa01sLstAcademicRecordSunOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srasa01sLstAcademicRecordSunInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srasa01sLstAcademicRecordSunOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
