package io.openems.edge.meter.eastron.sdm630;

import io.openems.common.types.OpenemsType;
import io.openems.common.utils.IntUtils;
import io.openems.edge.bridge.modbus.api.AbstractOpenemsModbusComponent;
import io.openems.edge.bridge.modbus.api.BridgeModbus;
import io.openems.edge.bridge.modbus.api.ElementToChannelConverter;
import io.openems.edge.bridge.modbus.api.ModbusProtocol;
import io.openems.edge.bridge.modbus.api.element.DummyRegisterElement;
import io.openems.edge.bridge.modbus.api.element.SignedDoublewordElement;
import io.openems.edge.bridge.modbus.api.element.WordOrder;
import io.openems.edge.bridge.modbus.api.task.FC4ReadInputRegistersTask;
import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.channel.doc.Doc;
import io.openems.edge.common.channel.doc.Unit;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.taskmanager.Priority;
import io.openems.edge.meter.api.AsymmetricMeter;
import io.openems.edge.meter.api.MeterType;
import io.openems.edge.meter.api.SymmetricMeter;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;

@Designate(ocd = Config.class, factory = true)
@Component(name = "Meter.Eastron.SDM630", immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class MeterEastronSDM630 extends AbstractOpenemsModbusComponent
        implements OpenemsComponent, AsymmetricMeter, SymmetricMeter {

    private MeterType meterType = MeterType.PRODUCTION;

    @Reference
    protected ConfigurationAdmin cm;

    public MeterEastronSDM630() {
        Utils.initializeChannels(this).forEach(channel -> this.addChannel(channel));
    }

    @Reference(policy = ReferencePolicy.STATIC, policyOption = ReferencePolicyOption.GREEDY, cardinality = ReferenceCardinality.MANDATORY)

    protected void setModbus(BridgeModbus modbus) {
        super.setModbus(modbus);
    }

    private String name;

    @Activate
    void activate(ComponentContext context, Config config) {
        this.meterType = config.type();
        super.activate(context, config.service_pid(), config.id(), config.enabled(), config.modbusUnitId(), this.cm,
                "Modbus", config.modbus_id());
        this._initializeMinMaxActivePower(this.cm, // Initialize Min/MaxActivePower channels
                config.service_pid(), config.minActivePower(), config.maxActivePower());
    }

    @Deactivate
    protected void deactivate() {
        super.deactivate();
    }

    public enum ChannelId implements io.openems.edge.common.channel.doc.ChannelId {
        MIN_ACTIVE_POWER(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.WATT)),
        MAX_ACTIVE_POWER(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.WATT)),
        ACTIVE_POWER(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.WATT)
                .onInit(channel -> {
                    channel.onSetNextValue(value -> {
                        /*
                         * Fill Min/Max Active Power channels
                         */
                        if (value.asOptional().isPresent()) {
                            float newValue = (Float) value.get();
                            {
                                Channel<Float> minActivePowerChannel = channel.getComponent()
                                        .channel(MeterEastronSDM630.ChannelId.MIN_ACTIVE_POWER);
                                float minActivePower = minActivePowerChannel.value().orElse((float) 0.0);
                                float minNextActivePower = minActivePowerChannel.getNextValue().orElse((float) 0.0);
                                if (newValue < Math.min(minActivePower, minNextActivePower)) {
                                    // avoid getting called too often -> round to 100
                                    newValue = IntUtils.roundToPrecision(newValue, IntUtils.Round.DOWN, 100);
                                    minActivePowerChannel.setNextValue(newValue);
                                }
                            }
                            {
                                Channel<Float> maxActivePowerChannel = channel.getComponent()
                                        .channel(MeterEastronSDM630.ChannelId.MAX_ACTIVE_POWER);
                                float maxActivePower = maxActivePowerChannel.value().orElse((float) 0);
                                float maxNextActivePower = maxActivePowerChannel.getNextValue().orElse((float) 0);
                                if (newValue > Math.max(maxActivePower, maxNextActivePower)) {
                                    // avoid getting called too often -> round to 100
                                    newValue = IntUtils.roundToPrecision(newValue, IntUtils.Round.UP, 100);
                                    maxActivePowerChannel.setNextValue(newValue);
                                }
                            }
                        }
                    });
                })),
        REACTIVE_POWER(new Doc().type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE_REACTIVE)),
        VOLTAGE(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT)),
        CURRENT(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.AMPERE)),
        APPARENT_POWER_L1(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE)),
        APPARENT_POWER_L2(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE)),
        APPARENT_POWER_L3(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE)),
        APPARENT_POWER(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE)),
        FREQUENCY(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.HERTZ)),
        ACTIVE_PRODUCTION_ENERGY(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.KILOWATT_HOURS)),
        REACTIVE_PRODUCTION_ENERGY(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.KILOWATT_HOURS)),
        ACTIVE_CONSUMPTION_ENERGY(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.KILOWATT_HOURS)),
        REACTIVE_CONSUMPTION_ENERGY(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.KILOWATT_HOURS)),
        ACTIVE_POWER_L1(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.WATT)),
        ACTIVE_POWER_L2(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.WATT)),
        ACTIVE_POWER_L3(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.WATT)),
        REACTIVE_POWER_L1(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE_REACTIVE)),
        REACTIVE_POWER_L2(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE_REACTIVE)),
        REACTIVE_POWER_L3(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT_AMPERE_REACTIVE)),
        VOLTAGE_L1(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT)),
        VOLTAGE_L2(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT)),
        VOLTAGE_L3(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.VOLT)),
        CURRENT_L1(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.AMPERE)),
        CURRENT_L2(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.AMPERE)),
        CURRENT_L3(new Doc()
                .type(OpenemsType.FLOAT)
                .unit(Unit.AMPERE));

        private final Doc doc;

        private ChannelId(Doc doc) {
            this.doc = doc;
        }

        public Doc doc() {
            return this.doc;
        }
    }

    @Override
    public MeterType getMeterType() {
        return this.meterType;
    }

    @Override
    protected ModbusProtocol defineModbusProtocol(int unitId) {
        final int OFFSET = 30000;
        return new ModbusProtocol(unitId,
                new FC4ReadInputRegistersTask(30001 - OFFSET, Priority.LOW,
                // VOLTAGE
                // Overall Voltage
                // measured from L1
                        m(MeterEastronSDM630.ChannelId.VOLTAGE,
                                new SignedDoublewordElement(30001 - OFFSET)
                                        .wordOrder(WordOrder.MSWLSW),
                                        ElementToChannelConverter.DIRECT_1_TO_1)),
                new FC4ReadInputRegistersTask(30001 - OFFSET, Priority.LOW,
                        // Phase 1 voltage
                        m(ChannelId.VOLTAGE_L1,
                                new SignedDoublewordElement(30001 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // Phase 2 voltage
                        m(ChannelId.VOLTAGE_L2,
                                new SignedDoublewordElement(30003 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // Phase 3 voltage
                        m(ChannelId.VOLTAGE_L3,
                                new SignedDoublewordElement(30005 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1)),
                new FC4ReadInputRegistersTask(30007 - OFFSET, Priority.HIGH,
                        // CURRENT
                        // Phase 1 current
                        m(ChannelId.CURRENT_L1,
                                new SignedDoublewordElement(30007 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // Phase 2 current
                        m(ChannelId.CURRENT_L2,
                                new SignedDoublewordElement(30009 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // Phase 3 current
                        m(ChannelId.CURRENT_L3,
                                new SignedDoublewordElement(30011 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // APPARENT POWER
                        // phase 1 VA
                        m(ChannelId.APPARENT_POWER_L1,
                                new SignedDoublewordElement(30013 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // phase 2 VA
                        m(ChannelId.APPARENT_POWER_L2,
                                new SignedDoublewordElement(30015 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // phase 3 VA
                        m(ChannelId.APPARENT_POWER_L3,
                                new SignedDoublewordElement(30017 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // ACTIVE POWER
                        // phase 1 active power
                        m(ChannelId.ACTIVE_POWER_L1,
                                new SignedDoublewordElement(30019 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // phase 2 active power
                        m(ChannelId.ACTIVE_POWER_L2,
                                new SignedDoublewordElement(30021 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // phase 3 active power
                        m(ChannelId.ACTIVE_POWER_L3,
                                new SignedDoublewordElement(30023 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        new DummyRegisterElement(30025 - OFFSET, 30030 - OFFSET),
                        // REACTIVE POWER
                        // phase 1 VAr
                        m(ChannelId.REACTIVE_POWER_L1,
                                new SignedDoublewordElement(30031 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // phase 2 VAr
                        m(ChannelId.REACTIVE_POWER_L2,
                                new SignedDoublewordElement(30033 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // phase 3 VAr
                        m(ChannelId.REACTIVE_POWER_L3,
                                new SignedDoublewordElement(30035 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        new DummyRegisterElement(30037 - OFFSET, 30048 - OFFSET),
                        // Overall Current
                        m(ChannelId.CURRENT,
                                new SignedDoublewordElement(30049 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        new DummyRegisterElement(30051 - OFFSET, 30052 - OFFSET),
                        // total system VA
                        m(ChannelId.APPARENT_POWER,
                                new SignedDoublewordElement(30053 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        new DummyRegisterElement(30055 - OFFSET, 30056 - OFFSET),
                        // total system active power
                        m(ChannelId.ACTIVE_POWER,
                                new SignedDoublewordElement(30057 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        new DummyRegisterElement(30059 - OFFSET, 30060 - OFFSET),
                        // total system VAr
                        m(ChannelId.REACTIVE_POWER,
                                new SignedDoublewordElement(30061 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1)),
                new FC4ReadInputRegistersTask(30071 - OFFSET, Priority.LOW,
                        // frequency
                        m(ChannelId.FREQUENCY, new SignedDoublewordElement(30071 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // active energy import/export
                        m(ChannelId.ACTIVE_PRODUCTION_ENERGY,
                                new SignedDoublewordElement(30073 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        m(ChannelId.ACTIVE_CONSUMPTION_ENERGY,
                                new SignedDoublewordElement(30075 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        // reactive energy import/export
                        m(ChannelId.REACTIVE_PRODUCTION_ENERGY,
                                new SignedDoublewordElement(30077 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1),
                        m(ChannelId.REACTIVE_CONSUMPTION_ENERGY,
                                new SignedDoublewordElement(30079 - OFFSET).wordOrder(WordOrder.MSWLSW),
                                ElementToChannelConverter.DIRECT_1_TO_1)));
    }

    @Override
    public String debugLog() {
        return "L:" + this.getActivePower().value().asString();
    }

}
