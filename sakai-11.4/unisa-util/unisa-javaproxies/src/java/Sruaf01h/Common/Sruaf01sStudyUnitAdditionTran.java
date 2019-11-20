package Sruaf01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Sruaf01sStudyUnitAdditionTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Sruaf01h.Servlet.Sruaf01sStudyUnitAdditionServlet";
    public String getServletName()     {return ServletName; }
 
    public Sruaf01sStudyUnitAdditionTran ()
   {
      super ();
      String myName = "Sruaf01sStudyUnitAddition()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Sruaf01sStudyUnitAdditionTran (serverDialog sysObj,
                                  Sruaf01sStudyUnitAdditionInput viewIn,
                                  Sruaf01sStudyUnitAdditionOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sruaf01sStudyUnitAdditionTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Sruaf01sStudyUnitAdditionTran (serverDialog sysObj,
                                  Sruaf01sStudyUnitAdditionInput viewIn,
                                  Sruaf01sStudyUnitAdditionOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Sruaf01sStudyUnitAdditionTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Sruaf01sStudyUnitAdditionInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Sruaf01sStudyUnitAdditionOutput x)
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
    if (e.getNewValue().getClass().getName() == "Sruaf01h.Sruaf01sStudyUnitAdditionInput")
      checkInViewValue ((Sruaf01sStudyUnitAdditionInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Sruaf01sStudyUnitAdditionInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Sruaf01h.Sruaf01sStudyUnitAdditionOutput")
      checkOutViewValue ((Sruaf01sStudyUnitAdditionOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Sruaf01sStudyUnitAdditionOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Sruaf01sStudyUnitAdditionInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Sruaf01sStudyUnitAdditionOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
