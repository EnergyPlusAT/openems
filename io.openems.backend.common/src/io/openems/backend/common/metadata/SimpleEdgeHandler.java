package io.openems.backend.common.metadata;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.event.EventReader;
import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.EdgeConfig;
import io.openems.common.types.EdgeConfigDiff;

public class SimpleEdgeHandler implements EdgeHandler {

	private final Logger log = LoggerFactory.getLogger(SimpleEdgeHandler.class);
	private final Map<String, EdgeConfig> data = new HashMap<>();

	public synchronized void setEdgeConfig(String edgeId, EdgeConfig edgeConfig) {
		this.data.put(edgeId, edgeConfig);
	}

	/**
	 * Sets the {@link EdgeConfig} from an {@link EventReader}.
	 * 
	 * @param reader the {@link EventReader}
	 */
	public synchronized void setEdgeConfigFromEvent(EventReader reader) {
		Edge edge = reader.getProperty(Edge.Events.OnSetConfig.EDGE);
		EdgeConfig newConfig = reader.getProperty(Edge.Events.OnSetConfig.CONFIG);

		try {
			EdgeConfig oldConfig = this.getEdgeConfig(edge.getId());
			EdgeConfigDiff diff = EdgeConfigDiff.diff(newConfig, oldConfig);
			if (!diff.isDifferent()) {
				return;
			}
			this.log.info("Edge [" + edge.getId() + "]. Update config: " + diff.toString());

		} catch (OpenemsNamedException e) {
			this.log.warn("Edge [" + edge.getId() + "]. Update config (unable to compoare old and new EdgeConfig): "
					+ e.getMessage());
			e.printStackTrace();
		}

		// Update EdgeConfig even without comparing
		this.setEdgeConfig(edge.getId(), newConfig);
	}

	@Override
	public synchronized EdgeConfig getEdgeConfig(String edgeId) throws OpenemsNamedException {
		return this.data.computeIfAbsent(edgeId, ignore -> new EdgeConfig());
	}

}
