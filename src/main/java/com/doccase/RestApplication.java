package com.doccase;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class RestApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register resources and features
		classes.add(MultiPartFeature.class);
		// classes.add(MultiPartResource.class);
		return classes;
	}

}
