package Srsrj11h.Common;
 
import com.ca.gen80.jprt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.Serializable;
import java.beans.*;
import com.ca.gen80.csu.trace.*;
 
public class Srsrj11sPrtNormalDeclarationTran   extends transaction
          implements java.io.Serializable
{
 
    final public String ServletName = "Srsrj11h.Servlet.Srsrj11sPrtNormalDeclarationServlet";
    public String getServletName()     {return ServletName; }
 
    public Srsrj11sPrtNormalDeclarationTran ()
   {
      super ();
      String myName = "Srsrj11sPrtNormalDeclaration()";
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, myName, "entered");
      }
    }
 
    public Srsrj11sPrtNormalDeclarationTran (serverDialog sysObj,
                                  Srsrj11sPrtNormalDeclarationInput viewIn,
                                  Srsrj11sPrtNormalDeclarationOutput viewOut,
                                  URL url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srsrj11sPrtNormalDeclarationTran(serverDialog, serverInputIF, serverOutputIF, URL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
    public Srsrj11sPrtNormalDeclarationTran (serverDialog sysObj,
                                  Srsrj11sPrtNormalDeclarationInput viewIn,
                                  Srsrj11sPrtNormalDeclarationOutput viewOut,
                                  String url,
                                  String path)
        throws PropertyVetoException
    {
        super (sysObj, viewIn, viewOut, url, path);
        String myName = "Srsrj11sPrtNormalDeclarationTran(serverDialog, serverInputIF, serverOutputIF, StringURL, StringPath)";
        if (Trace.isTracing(Trace.MASK_APPLICATION))
        {
           Trace.record(Trace.MASK_APPLICATION, myName, sysObj+":"+ viewIn +":"+ viewOut +":"+url +":"+path);
        }
    }
 
  //--------------------------------------------------
  // Input properties accessor methods
 
  public synchronized void setInView (Srsrj11sPrtNormalDeclarationInput x)
       throws PropertyVetoException
  {
    checkInViewValue (x);
    super.setInView (x);
  }
 
  public synchronized void setOutView (Srsrj11sPrtNormalDeclarationOutput x)
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
    if (e.getNewValue().getClass().getName() == "Srsrj11h.Srsrj11sPrtNormalDeclarationInput")
      checkInViewValue ((Srsrj11sPrtNormalDeclarationInput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "inView: Bad type in PropertyChangeEvent, must be Srsrj11sPrtNormalDeclarationInput Type", e);
  }
 
  public synchronized void OutViewVetoableChange (PropertyChangeEvent e)
       throws PropertyVetoException
  {
    if (e.getNewValue().getClass().getName() == "Srsrj11h.Srsrj11sPrtNormalDeclarationOutput")
      checkOutViewValue ((Srsrj11sPrtNormalDeclarationOutput)e.getNewValue());
    else
      throw new PropertyVetoException (
          "outView: Bad type in PropertyChangeEvent, must be Srsrj11sPrtNormalDeclarationOutput Type", e);
  }
 
  //--------------------------------------------------
  // check value of inputed property values
 
  protected synchronized void checkInViewValue (Srsrj11sPrtNormalDeclarationInput x)
       throws PropertyVetoException
  {
    super.checkInViewValue ((serverInputIF)x);
 
    // do any additional checks needed here
  }
 
  protected synchronized void checkOutViewValue (Srsrj11sPrtNormalDeclarationOutput x)
       throws PropertyVetoException
  {
    super.checkOutViewValue ((serverOutputIF)x);
 
    // do any additional checks needed here
  }
}
