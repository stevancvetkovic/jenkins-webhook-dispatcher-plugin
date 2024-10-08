package tech.simpledevops.jenkins.plugin;

import hudson.model.Api;

import hudson.model.FreeStyleProject;
import hudson.model.TopLevelItem;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.WebMethod;
import org.kohsuke.stapler.interceptor.RequirePOST;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import jenkins.model.*;

import static java.util.logging.Level.INFO;

import org.jenkinsci.plugins.workflow.job.WorkflowJob;

public class WebhookApi extends Api {

    private WebhookAction webhookAction;
    private final static Logger log = Logger.getLogger(WebhookApi.class.getName());

    public WebhookApi(Object bean) {
        super(bean);
        this.webhookAction = WebhookAction.getInstance();
    }

    @WebMethod(name = "postWebhookPayload")
    @RequirePOST
    public void doPostWebhookPayload(StaplerRequest req, StaplerResponse rsp) throws IOException {
        try {
            String ct = req.getContentType();
            if ((ct == null) || !ct.startsWith("application/json"))
                throw new ServletException("Expected application/json but got "+ct);

            JSONObject o = JSONObject.fromObject(IOUtils.toString(req.getReader()));
            String content = o.get("action").toString();

            Jenkins instance = Jenkins.getInstanceOrNull();

            if (instance != null) {
                TopLevelItem job = instance.getItemByFullName("AMS/build-bigbear", WorkflowJob.class);
                if (job != null) log.log(INFO, job.getDisplayName());
            }

            log.log(INFO, "Get webhook info.");
            rsp.setContentType("text/plain;charset=UTF-8");
            PrintWriter pw = rsp.getWriter();
            pw.print(content);
            pw.flush();
            pw.close();
        } catch(ServletException e) {
            log.log(INFO, "Failed to get JSON payload!");
        }
    }
}
