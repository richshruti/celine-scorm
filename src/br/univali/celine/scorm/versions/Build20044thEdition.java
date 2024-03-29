/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.celine.scorm.versions;

import br.univali.celine.scorm.dataModel.DataModelCommandManager;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.dataModel.adl.nav.RequestValid;
import br.univali.celine.scorm.dataModel.cmi.CommentsFromLMS;
import br.univali.celine.scorm.dataModel.cmi.CommentsFromLearner;
import br.univali.celine.scorm.dataModel.cmi.CompletionStatus;
import br.univali.celine.scorm.dataModel.cmi.CompletionThreshold;
import br.univali.celine.scorm.dataModel.cmi.Credit;
import br.univali.celine.scorm.dataModel.cmi.Entry;
import br.univali.celine.scorm.dataModel.cmi.Exit;
import br.univali.celine.scorm.dataModel.cmi.Interactions;
import br.univali.celine.scorm.dataModel.cmi.LaunchData;
import br.univali.celine.scorm.dataModel.cmi.LearnerId;
import br.univali.celine.scorm.dataModel.cmi.LearnerName;
import br.univali.celine.scorm.dataModel.cmi.LearnerPreference;
import br.univali.celine.scorm.dataModel.cmi.Location;
import br.univali.celine.scorm.dataModel.cmi.MaxTimeAllowed;
import br.univali.celine.scorm.dataModel.cmi.Mode;
import br.univali.celine.scorm.dataModel.cmi.Objectives;
import br.univali.celine.scorm.dataModel.cmi.ProgressMeasure;
import br.univali.celine.scorm.dataModel.cmi.ScaledPassingScore;
import br.univali.celine.scorm.dataModel.cmi.Score;
import br.univali.celine.scorm.dataModel.cmi.SessionTime;
import br.univali.celine.scorm.dataModel.cmi.SuccessStatus;
import br.univali.celine.scorm.dataModel.cmi.SuspendData;
import br.univali.celine.scorm.dataModel.cmi.TimeLimitAction;
import br.univali.celine.scorm.dataModel.cmi.TotalTime;
import br.univali.celine.scorm.dataModel.cmi.Version;
import br.univali.celine.scorm.model.cam.AbstractItem;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.db.ClearSuspendedActivitySubprocess;
import br.univali.celine.scorm.sn.db.ContentDeliveryEnvironmentProcess;
import br.univali.celine.scorm.sn.db.DeliveryRequestProcess;
import br.univali.celine.scorm.sn.op.OverallSequencingProcess;
import br.univali.celine.scorm.sn.rb.ActivityProgressRollupProcess;
import br.univali.celine.scorm.sn.rb.CheckChildRollupSubprocess;
import br.univali.celine.scorm.sn.rb.EvaluateRollupConditionsSubprocess;
import br.univali.celine.scorm.sn.rb.MeasureRollupProcess;
import br.univali.celine.scorm.sn.rb.OverallRollupProcess;
import br.univali.celine.scorm.sn.rb.RollupRuleCheckSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceActivityTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceFlowSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceFlowTreeTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.FlowActivityTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.FlowSubprocess;
import br.univali.celine.scorm.sn.sb.FlowTreeTraversalSubprocess;
import br.univali.celine.scorm.sn.sr.RandomizeChildrenProcess;
import br.univali.celine.scorm.sn.sr.SelectChildrenProcess;
import br.univali.celine.scorm.sn.tb.SequencingExitActionRulesSubprocess;
import br.univali.celine.scorm.sn.tb.SequencingPostConditionRulesSubprocess;
import br.univali.celine.scorm.sn.tb.TerminationRequestProcess;
import br.univali.celine.scorm.sn.up.CheckActivityProcess;
import br.univali.celine.scorm.sn.up.EndAttemptProcess;
import br.univali.celine.scorm.sn.up.LimitConditionsCheckProcess;
import br.univali.celine.scorm.sn.up.SequencingRuleCheckSubprocess;
import br.univali.celine.scorm.sn.up.SequencingRulesCheckProcess;
import br.univali.celine.scorm.sn.up.TerminateDescendentAttemptsProcess;
import br.univali.celine.scorm2004_4th.dataModel.adl.Data;
import br.univali.celine.scorm2004_4th.model.cam.Item20044th;
import br.univali.celine.scorm2004_4th.sn.nb.NavigationRequestProcess20044th;
import br.univali.celine.scorm2004_4th.sn.sb.seqreqprocess.SequencingRequestProcess20044th;

public class Build20044thEdition implements BuildVersion {

	public static Build20044thEdition create() {
		if (build == null)
			build = new Build20044thEdition();
		return build;
	}
	
	private static Build20044thEdition build;
	
	private Build20044thEdition() {
		
		buildDM();
		buildSN();
		
	}

	private void buildSN() {
		ProcessProvider.clearInstance();
		ProcessProvider pp = ProcessProvider.getInstance();
		
		pp.setActivityProgressRollupProcess(new ActivityProgressRollupProcess());
		pp.setCheckActivityProcess(new CheckActivityProcess());
		pp.setCheckChildRollupSubprocess(new CheckChildRollupSubprocess());
		pp.setChoiceActivityTraversalSubprocess(new ChoiceActivityTraversalSubprocess());
		pp.setChoiceFlowSubprocess(new ChoiceFlowSubprocess());
		pp.setChoiceFlowTreeTraversalSubprocess(new ChoiceFlowTreeTraversalSubprocess());
		pp.setClearSuspendedActivitySubprocess(new ClearSuspendedActivitySubprocess());
		pp.setContentDeliveryEnvironmentProcess(new ContentDeliveryEnvironmentProcess());
		pp.setDeliveryRequestProcess(new DeliveryRequestProcess());
		pp.setEndAttemptProcess(new EndAttemptProcess());
		pp.setEvaluateRollupConditionsSubprocess(new EvaluateRollupConditionsSubprocess());
		pp.setFlowActivityTraversalSubprocess(new FlowActivityTraversalSubprocess());
		pp.setFlowSubprocess(new FlowSubprocess());
		pp.setFlowTreeTraversalSubprocess(new FlowTreeTraversalSubprocess());
		pp.setLimitConditionsCheckProcess(new LimitConditionsCheckProcess());
		pp.setMeasureRollupProcess(new MeasureRollupProcess());
		pp.setNavigationRequestProcess(new NavigationRequestProcess20044th());
		pp.setOverallRollupProcess(new OverallRollupProcess());
		pp.setOverallSequencingProcess(new OverallSequencingProcess());
		pp.setRandomizeChildrenProcess(new RandomizeChildrenProcess());
		pp.setRollupRuleCheckSubprocess(new RollupRuleCheckSubprocess());
		pp.setSelectChildrenProcess(new SelectChildrenProcess());
		pp.setSequencingExitActionRulesSubprocess(new SequencingExitActionRulesSubprocess());
		pp.setSequencingPostConditionRulesSubprocess(new SequencingPostConditionRulesSubprocess());
		pp.setSequencingRequestProcess(new SequencingRequestProcess20044th());
		pp.setSequencingRuleCheckSubprocess(new SequencingRuleCheckSubprocess());
		pp.setSequencingRulesCheckProcess(new SequencingRulesCheckProcess());
		pp.setTerminateDescendentAttemptsProcess(new TerminateDescendentAttemptsProcess());
		pp.setTerminationRequestProcess(new TerminationRequestProcess());
	}

	private void buildDM() {
		DataModelCommandManager.clearGlobalInstance();
		DataModelCommandManager dm = DataModelCommandManager.getGlobalInstance();
		
		dm.put(Request.name, new Request());
		dm.put(RequestValid.name, new RequestValid());
		
		dm.put(CommentsFromLearner.name, new CommentsFromLearner());
		dm.put(CommentsFromLMS.name, new CommentsFromLMS());
		dm.put(CompletionStatus.name, new CompletionStatus());
		dm.put(CompletionThreshold.name, new CompletionThreshold());
		dm.put(Entry.name, new Entry());
		dm.put(Credit.name, new Credit());
		dm.put(Exit.name, new Exit());
		dm.put(Interactions.name, new Interactions());
		dm.put(LaunchData.name, new LaunchData());
		dm.put(LearnerId.name, new LearnerId());
		dm.put(LearnerName.name, new LearnerName());
		dm.put("", new LearnerPreference()); // TODO
		dm.put(Location.name, new Location()); // TODO precisa rever para tirar o esquema de getDataModel e dm.putDataModel
		dm.put(MaxTimeAllowed.name, new MaxTimeAllowed());
		dm.put(Mode.name, new Mode()); // TODO
		dm.put(Objectives.name, new Objectives());
		dm.put(ProgressMeasure.name, new ProgressMeasure());
		dm.put(ScaledPassingScore.name, new ScaledPassingScore());
		dm.put(Score.name, new Score());
		dm.put(SessionTime.name, new SessionTime()); // TODO rever para tirar o esquema de getDataModel e dm.put
		dm.put(SuccessStatus.name, new SuccessStatus());
		dm.put(SuspendData.name, new SuspendData()); // TODO
		dm.put(TimeLimitAction.name, new TimeLimitAction());
		dm.put(TotalTime.name, new TotalTime()); // TODO rever para tirar o esquema de get e dm.put DataModel

		dm.put(Version.name, new Version());
		
		dm.put(Data.name, new Data());
	}
	
	@Override
	public int getVersion() {
		return 2004;
	}
	
	@Override
	public AbstractItem buildItem() {
		
		return new Item20044th();
	}

	@Override
	public String[] getXSDFileNames() {
		String folder = "/br/univali/celine/scorm";
		
		return new String[] { 
				  folder + "/adlcp_v1p3.xsd", 
				  folder + "/adlnav_v1p3.xsd",
				  folder + "/adlseq_v1p3.xsd",
				  folder + "/imscp_v1p1.xsd",
				  folder + "/imsss_v1p0.xsd",

				  folder + "/imsss_v1p0auxresource.xsd",
				  folder + "/imsss_v1p0control.xsd",
				  folder + "/imsss_v1p0delivery.xsd",
				  folder + "/imsss_v1p0limit.xsd",
				  folder + "/imsss_v1p0objective.xsd",
				  folder + "/imsss_v1p0random.xsd",
				  folder + "/imsss_v1p0rollup.xsd",
				  folder + "/imsss_v1p0seqrule.xsd",
				  
				  folder + "/imsss_v1p0util.xsd"
		};
	}

	@Override
	public String getMetadataSchemaVersion() {
		return "2004 4th Edition";
	}

}
