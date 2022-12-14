package io.openems.edge.kaco.blueplanet.hybrid10.pvinverter;

import io.openems.common.channel.Level;
import io.openems.edge.common.channel.Doc;

public interface BpPvInverter {

	public static final int MAX_APPARENT_POWER = 10_000; // [W]

	public static enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		PV_LIMIT_FAILED(Doc.of(Level.FAULT) //
				.text("PV-Limit failed"));

		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}
	}

}