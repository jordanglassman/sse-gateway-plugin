package org.jenkinsci.plugins.ssegateway.sample;

import org.jenkinsci.plugins.pubsub.PubsubBus;
import org.jenkinsci.plugins.pubsub.exception.MessageException;
import org.jenkinsci.plugins.pubsub.message.JenkinsMessage;
import org.jenkinsci.plugins.pubsub.message.JobMessage;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class PubsubDemoServlet extends GenericServlet {
    @Override
    public void init() throws ServletException {
        System.setProperty("org.jenkinsci.plugins.pubsub.in.jenkins", "true");
        final PubsubBus bus = PubsubBus.getBus();

        try {
            while (true) {
                System.out.println("sending job_run_started message");
                JenkinsMessage startMessage = new JobMessage();
                startMessage.setChannelName("job");
                startMessage.setEventName("job_run_started");
                bus.publish(startMessage);

                Thread.sleep(200);

                System.out.println("sending job_run_started message");
                JenkinsMessage stopMessage = new JobMessage();
                stopMessage.setChannelName("job");
                stopMessage.setEventName("job_run_ended");
                bus.publish(stopMessage);

                Thread.sleep(200);
            }
        } catch (InterruptedException | MessageException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        System.clearProperty("org.jenkinsci.plugins.pubsub.in.jenkins");
    }

    @Override
    public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {

    }
}
