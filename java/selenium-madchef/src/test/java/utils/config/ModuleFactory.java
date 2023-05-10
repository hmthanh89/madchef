package utils.config;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ModuleFactory implements Module {

	@Override
	public void configure(Binder binder) {
		// Bind test data
		//binder.bind(GeneralPage.class);
	}
}
