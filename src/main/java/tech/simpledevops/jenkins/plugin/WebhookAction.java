package tech.simpledevops.jenkins.plugin;


import hudson.Extension;
import hudson.model.RootAction;

@Extension
public class WebhookAction implements RootAction {
    private static WebhookAction instance = new WebhookAction();

    public static WebhookAction getInstance() {
        return instance;
    }

    public String getBuildInfo() {
        return "test123";
    }

    @Override
    public String getUrlName() {
        return "webhookdispatcher";
    }

    @Override
    public String getDisplayName() {
        return "Webhook dispatcher";
    }

    @Override
    public String getIconFileName() {
        return "/plugin/customerci-version/customerci-icon.png";
    }
}
