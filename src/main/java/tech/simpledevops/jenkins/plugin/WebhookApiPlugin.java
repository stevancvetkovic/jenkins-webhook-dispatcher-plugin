package tech.simpledevops.jenkins.plugin;

import hudson.Plugin;
import hudson.model.Api;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean
public class WebhookApiPlugin extends Plugin {

    public Api getApi() {
        return new WebhookApi(this);
    }
}
