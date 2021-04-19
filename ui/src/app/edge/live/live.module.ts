import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './../../shared/shared.module';
import { ChannelthresholdComponent } from './channelthreshold/channelthreshold.component';
import { ChpSocComponent } from './chpsoc/chpsoc.component';
import { ChpsocModalComponent } from './chpsoc/modal/modal.component';
import { AutarchyComponent } from './common/autarchy/autarchy.component';
import { AutarchyModalComponent } from './common/autarchy/modal/modal.component';
import { ConsumptionComponent } from './consumption/consumption.component';
import { ConsumptionModalComponent } from './consumption/modal/modal.component';
import { Controller_Ess_FixActivePower } from './Controller_Ess_FixActivePower/Controller_Ess_FixActivePower';
import { Controller_Ess_FixActivePowerModalComponent } from './Controller_Ess_FixActivePower/modal/modal.component';
import { Controller_Io_FixDigitalOutput } from './Controller_Io_FixDigitalOutput/Controller_Io_FixDigitalOutput';
import { Controller_Io_FixDigitalOutputModalComponent } from './Controller_Io_FixDigitalOutput/modal/modal.component';
import { DelayedSellToGridComponent } from './delayedselltogrid/delayedselltogrid.component';
import { DelayedSellToGridModalComponent } from './delayedselltogrid/modal/modal.component';
import { DigitalInputComponent } from './digitalinput/digitalinput.component';
import { DigitalInputModalComponent } from './digitalinput/modal/modal.component';
import { EnergymonitorModule } from './energymonitor/energymonitor.module';
import { EvcsComponent } from './evcs/evcs.component';
import { AdministrationComponent } from './evcs/modal/administration/administration.component';
import { EvcsModalComponent } from './evcs/modal/modal.page';
import { EvcsPopoverComponent } from './evcs/modal/popover/popover.page';
import { EvcsClusterComponent } from './evcsCluster/evcsCluster.component';
import { EvcsChart } from './evcsCluster/modal/evcs-chart/evcs.chart';
import { ModalComponentEvcsCluster } from './evcsCluster/modal/evcsCluster-modal.page';
import { FlatWidgetLine } from './flat/flat-widget-line/flat-widget-line';
import { FlatWidgetPercentagebar } from './flat/flat-widget-line/flat-widget-percentagebar';
import { FlatWidgetComponent } from './flat/flat-widget.component';
import { GridComponent } from './common/grid/grid.component';
import { GridModalComponent } from './common/grid/modal/modal.component';
import { HeatingElementComponent } from './heatingelement/heatingelement.component';
import { HeatingElementModalComponent } from './heatingelement/modal/modal.component';
import { HeatPumpComponent } from './heatpump/heatpump.component';
import { HeatPumpModalComponent } from './heatpump/modal/modal.component';
import { InfoComponent } from './info/info.component';
import { LiveComponent } from './live.component';
import { OfflineComponent } from './offline/offline.component';
import { AsymmetricPeakshavingComponent } from './peakshaving/asymmetric/asymmetricpeakshaving.component';
import { AsymmetricPeakshavingModalComponent } from './peakshaving/asymmetric/modal/modal.component';
import { SymmetricPeakshavingModalComponent } from './peakshaving/symmetric/modal/modal.component';
import { SymmetricPeakshavingComponent } from './peakshaving/symmetric/symmetricpeakshaving.component';
import { TimeslotPeakshavingModalComponent } from './peakshaving/timeslot/modal/modal.component';
import { TimeslotPeakshavingComponent } from './peakshaving/timeslot/timeslotpeakshaving.component';
import { ProductionModalComponent } from './production/modal/modal.component';
import { ProductionComponent } from './production/production.component';
import { SelfconsumptionModalComponent } from './selfconsumption/modal/modal.component';
import { SelfConsumptionComponent } from './selfconsumption/selfconsumption.component';
import { SinglethresholdModalComponent } from './singlethreshold/modal/modal.component';
import { SinglethresholdComponent } from './singlethreshold/singlethreshold.component';
import { StorageModalComponent } from './storage/modal/modal.component';
import { StorageComponent } from './storage/storage.component';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    EnergymonitorModule,
    SharedModule,
  ],
  entryComponents: [
    AdministrationComponent,
    AsymmetricPeakshavingModalComponent,
    AutarchyModalComponent,
    ChpsocModalComponent,
    ConsumptionModalComponent,
    Controller_Ess_FixActivePower,
    Controller_Ess_FixActivePowerModalComponent,
    Controller_Io_FixDigitalOutput,
    Controller_Io_FixDigitalOutputModalComponent,
    DelayedSellToGridModalComponent,
    DigitalInputComponent,
    DigitalInputModalComponent,
    EvcsModalComponent,
    EvcsPopoverComponent,
    FlatWidgetComponent,
    FlatWidgetLine,
    FlatWidgetPercentagebar,
    GridModalComponent,
    HeatingElementModalComponent,
    HeatPumpModalComponent,
    ModalComponentEvcsCluster,
    ProductionModalComponent,
    SelfconsumptionModalComponent,
    SinglethresholdModalComponent,
    StorageModalComponent,
    SymmetricPeakshavingModalComponent,
    TimeslotPeakshavingModalComponent,
  ],
  declarations: [
    AdministrationComponent,
    AsymmetricPeakshavingComponent,
    AsymmetricPeakshavingModalComponent,
    AutarchyComponent,
    AutarchyModalComponent,
    ChannelthresholdComponent,
    ChpSocComponent,
    ChpsocModalComponent,
    ConsumptionComponent,
    ConsumptionModalComponent,
    Controller_Ess_FixActivePower,
    Controller_Ess_FixActivePowerModalComponent,
    Controller_Io_FixDigitalOutput,
    Controller_Io_FixDigitalOutputModalComponent,
    DelayedSellToGridComponent,
    DelayedSellToGridModalComponent,
    DigitalInputComponent,
    DigitalInputModalComponent,
    EvcsChart,
    EvcsClusterComponent,
    EvcsComponent,
    EvcsModalComponent,
    EvcsPopoverComponent,
    FlatWidgetComponent,
    FlatWidgetLine,
    FlatWidgetPercentagebar,
    GridComponent,
    GridModalComponent,
    HeatingElementComponent,
    HeatingElementModalComponent,
    HeatPumpComponent,
    HeatPumpModalComponent,
    InfoComponent,
    LiveComponent,
    ModalComponentEvcsCluster,
    OfflineComponent,
    ProductionComponent,
    ProductionModalComponent,
    SelfConsumptionComponent,
    SelfconsumptionModalComponent,
    SinglethresholdComponent,
    SinglethresholdModalComponent,
    StorageComponent,
    StorageModalComponent,
    SymmetricPeakshavingComponent,
    SymmetricPeakshavingModalComponent,
    TimeslotPeakshavingComponent,
    TimeslotPeakshavingModalComponent,
  ]
})
export class LiveModule { }
