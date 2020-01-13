package za.ac.unisa.lms.tools.cronjobs.servlets;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.InterruptableJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class SchedulerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Log log = null;
	private Scheduler scheduler = null;

	private String getRequiredAttribute(Element element, String attribute) throws Exception {
		String value = element.getAttribute(attribute);
		if (value == null) {
			throw new Exception("Attribute \""+attribute+"\" of element \""+element.getTagName()+"\" is required");
		}
		value = value.trim();
		if (value.equals("")) {
			throw new Exception("Attribute \""+attribute+"\" of element \""+element.getTagName()+"\" must have a non-whitespace value");
		}

		return value;
	}

	public void init(ServletConfig arg0) throws ServletException {
		log = LogFactory.getLog(this.getClass());
		log.debug("Initializing");

		try {

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			scheduler = schedulerFactory.getScheduler();
			JobDetail jobDetail = null;
			CronTrigger cronTrigger = null;

			InputStream is = this.getClass().getResourceAsStream("/crontab.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			InputSource inputSource = new InputSource(is);
			Document doc = docBuilder.parse(inputSource);

			Element root = doc.getDocumentElement();

			if (!root.getTagName().equalsIgnoreCase("crontab")) {
				throw new Exception("Invalid root element for crontab.xml: expected \"crontab\" as root.");
			}

			NodeList rootNodes = root.getChildNodes();
			final int rootNodesLength = rootNodes.getLength();
			for (int i = 0; i < rootNodesLength; i++)
			{
				Node rootNode = rootNodes.item(i);
				if (rootNode.getNodeType() != Node.ELEMENT_NODE) continue;
				Element rootElement = (Element) rootNode;

				// look for "category" elements
				if (rootElement.getTagName().equals("cronjob"))
				{
					String schedule = getRequiredAttribute(rootElement, "schedule");
					String className = getRequiredAttribute(rootElement, "class");

					jobDetail = new JobDetail(className+".job", Scheduler.DEFAULT_GROUP, Class.forName(className));
					cronTrigger = new CronTrigger(className+".cron", Scheduler.DEFAULT_GROUP);
					cronTrigger.setCronExpression(schedule);
					scheduler.scheduleJob(jobDetail, cronTrigger);
					log.info(className+" scheduled: "+schedule);

				}
			}

			log.info("Scheduler started.");

		} catch (Exception e) {
			throw new ServletException(e);
		}

		super.init(arg0);
	}

	public void destroy() {
		log.debug("Destroy");

		try {

			List<?> jobs = scheduler.getCurrentlyExecutingJobs();
			log.info("Shutting down scheduler");
			scheduler.shutdown(false);

			Iterator<?> ji = jobs.iterator();
			while (ji.hasNext()) {
				JobExecutionContext jec = (JobExecutionContext) ji.next();
				InterruptableJob job = (InterruptableJob) jec.getJobInstance();
				log.info("Stopping job: "+ jec.getJobDetail().getFullName());
				job.interrupt();
			}

		} catch (SchedulerException se) {
			log.error("Scheduler exception: "+se.getClass().getName()+": "+se.getMessage());
		}
		super.destroy();
		log.info("Stopped.");
	}


}
