package io.openems.edge.ess.generic.offgrid;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.openems.edge.common.startstop.StartStopConfig;

@ObjectClassDefinition(//
		name = "ESS Generic Off Grid", //
		description = "")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "ess0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	@AttributeDefinition(name = "Start/stop behaviour?", description = "Should this component be forced to start or stop?")
	StartStopConfig startStop() default StartStopConfig.START;

	@AttributeDefinition(name = "Battery-Inverter-ID", description = "ID of Battery-Inverter.")
	String batteryInverter_id() default "batteryInverter0";

	@AttributeDefinition(name = "Battery-ID", description = "ID of Battery.")
	String battery_id() default "battery0";

	@AttributeDefinition(name = "OffGridSwitch-ID", description = "IO Off Grid Switch.")
	String offGridSwitch_id() default "offGridSwitch0";

	@AttributeDefinition(name = "Battery-Inverter target filter", description = "This is auto-generated by 'Battery-Inverter-ID'.")
	String batteryInverter_target() default "(enabled=true)";

	@AttributeDefinition(name = "Battery target filter", description = "This is auto-generated by 'Battery-ID'.")
	String battery_target() default "(enabled=true)";

	@AttributeDefinition(name = "OffGridSwitch target filter", description = "This is auto-generated by 'OffGridSwitch-ID'.")
	String offGridSwitch_target() default "(enabled=true)";

	String webconsole_configurationFactory_nameHint() default "ESS Generic Off Grid [{id}]";

}