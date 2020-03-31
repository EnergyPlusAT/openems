package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconPCS;
import io.openems.edge.ess.mr.gridcon.IState;

public class OffGridGridBack extends BaseState {

	private float targetFrequencyOffgrid;

	public OffGridGridBack(ComponentManager manager, DecisionTableCondition condition, String gridconPCSId, String b1Id,
			String b2Id, String b3Id, String inputNA1, String inputNA2, String inputSyncBridge, String outputSyncBridge,
			String meterId, float targetFrequencyOffgrid) {
		super(manager, condition, gridconPCSId, b1Id, b2Id, b3Id, inputNA1, inputNA2, inputSyncBridge, outputSyncBridge,
				meterId);
		this.targetFrequencyOffgrid = targetFrequencyOffgrid;
	}

	@Override
	public IState getState() {
		return OnOffGridState.OFF_GRID_MODE_GRID_BACK;
	}

	@Override
	public IState getNextState() {
		if (DecisionTableHelper.isUndefined(condition)) {
			return OnOffGridState.UNDEFINED;
		}
		
		if (DecisionTableHelper.isOffGridGridBack(condition)) {
			return OnOffGridState.OFF_GRID_MODE_GRID_BACK;
		}
		
		if (DecisionTableHelper.isOffGridWaitForGridAvailable(condition)) {
			return OnOffGridState.OFF_GRID_MODE_WAIT_FOR_GRID_AVAILABLE;
		}
		
		if (DecisionTableHelper.isOffGridMode(condition)) {
			return OnOffGridState.OFF_GRID_MODE;
		}
		
		return OnOffGridState.OFF_GRID_MODE_GRID_BACK;
	}

	@Override
	public void act() throws OpenemsNamedException {
		setSyncBridge(true);
		float factor = targetFrequencyOffgrid / GridconPCS.DEFAULT_GRID_FREQUENCY;
		getGridconPCS().setF0(factor);
		try {
			getGridconPCS().doWriteTasks();
		} catch (OpenemsNamedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}