package in.ajjain.ci.view.service;

import in.ajjain.ci.view.resources.ClickInsightsResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * The Class ClickInsightsApplication.
 */
public class ClickInsightsApplication extends Application<ClickInsightsConfiguration>{
	
	/* (non-Javadoc)
	 * @see io.dropwizard.Application#initialize(io.dropwizard.setup.Bootstrap)
	 */
	@Override
	public void initialize(Bootstrap<ClickInsightsConfiguration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
	}
	
	/* (non-Javadoc)
	 * @see io.dropwizard.Application#run(io.dropwizard.Configuration, io.dropwizard.setup.Environment)
	 */
	@Override
	public void run(ClickInsightsConfiguration config, Environment env)	throws Exception {
		env.jersey().setUrlPattern("/api/*");
		final ClickInsightsResource resource = new ClickInsightsResource();
		env.jersey().register(resource);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		new ClickInsightsApplication().run(args);
	}

	/* (non-Javadoc)
	 * @see io.dropwizard.Application#getName()
	 */
	@Override
	public String getName() {
		return "ClickInsights";
	}
}
