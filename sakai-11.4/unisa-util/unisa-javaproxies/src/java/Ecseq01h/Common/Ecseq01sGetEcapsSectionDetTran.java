package Ecseq01h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Ecseq01sGetEcapsSectionDetTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Ecseq01h.Servlet.Ecseq01sGetEcapsSectionDetServlet";
    public String getServletName()     {return ServletName; }
 
    public Ecseq01sGetEcapsSectionDetTran ()
   {
      super ();
      String myName = "Ecseq01sGetEcapsSectionDet()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Ecseq01sGetEcapsSectionDetTran (serverDialog sysObj,
                                  Ecseq01sGetEcapsSectionDetInput viewIn,
                                  Ecseq01sGetEcapsSectionDetOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Ecseq01sGetEcapsSectionDetTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Ecseq01sGetEcapsSectionDetTran (serverDialog sysObj,
                                  Ecseq01sGetEcapsSectionDetInput viewIn,
                                  Ecseq01sGetEcapsSectionDetOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Ecseq01sGetEcapsSectionDetTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Ecseq01sGetEcapsSectionDetInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Ecseq01sGetEcapsSectionDetOutput x)
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
    if (e.getNewValue().getClass().getName() == "Ecseq01h.Ecseq01sGetEcapsSectionDetInput")
      checkInViewValue ((Ecseq01sGetEcapsSectionDetInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Ecseq01sGetEcapsSectionDetInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Ecseq01h.Ecseq01sGetEcapsSectionDetOutput")
      checkOutViewValue ((Ecseq01sGetEcapsSectionDetOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Ecseq01sGetEcapsSectionDetOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Ecseq01sGetEcapsSectionDetInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Ecseq01sGetEcapsSectionDetOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
