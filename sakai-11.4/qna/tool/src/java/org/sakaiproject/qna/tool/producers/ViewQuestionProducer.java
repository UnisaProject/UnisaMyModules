/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.tool.producers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sakaiproject.qna.comparators.AnswersListComparator;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.model.QnaAnswer;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.tool.otp.AnswerLocator;
import org.sakaiproject.qna.tool.params.AnswerParams;
import org.sakaiproject.qna.tool.params.QuestionParams;
import org.sakaiproject.qna.tool.producers.renderers.AttachmentsViewRenderer;
import org.sakaiproject.qna.tool.producers.renderers.NavBarRenderer;
import org.sakaiproject.qna.tool.producers.renderers.QuestionIteratorRenderer;
import org.sakaiproject.qna.tool.producers.renderers.SearchBarRenderer;
import org.sakaiproject.qna.tool.utils.DateUtil;

import uk.org.ponder.messageutil.TargettedMessage;
import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.ELReference;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.components.decorators.DecoratorList;
import uk.org.ponder.rsf.components.decorators.UIAlternativeTextDecorator;
import uk.org.ponder.rsf.components.decorators.UITooltipDecorator;
import uk.org.ponder.rsf.evolvers.TextInputEvolver;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.ActionResultInterceptor;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class ViewQuestionProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter, ActionResultInterceptor {

	public static final String VIEW_ID = "view_question";
	private SearchBarRenderer searchBarRenderer;
	private QuestionIteratorRenderer questionIteratorRenderer;
    private NavBarRenderer navBarRenderer;
    private TextInputEvolver richTextEvolver;
    private PermissionLogic permissionLogic;
    private ExternalLogic externalLogic;
    private QuestionLogic questionLogic;
    private AttachmentsViewRenderer attachmentsViewRenderer;
    private OptionsLogic optionsLogic;
   
    
	public String getViewID() {
		return VIEW_ID;
	}

	public void setQuestionIteratorRenderer(QuestionIteratorRenderer questionIteratorRenderer) {
		this.questionIteratorRenderer = questionIteratorRenderer;
	}

    public void setNavBarRenderer(NavBarRenderer navBarRenderer) {
		this.navBarRenderer = navBarRenderer;
	}

    public void setSearchBarRenderer(SearchBarRenderer searchBarRenderer) {
		this.searchBarRenderer = searchBarRenderer;
	}

    public void setRichTextEvolver(TextInputEvolver richTextEvolver) {
        this.richTextEvolver = richTextEvolver;
    }

    public void setPermissionLogic(PermissionLogic permissionLogic) {
        this.permissionLogic = permissionLogic;
    }

    public void setExternalLogic(ExternalLogic externalLogic) {
        this.externalLogic = externalLogic;
    }

    public void setQuestionLogic(QuestionLogic questionLogic) {
    	this.questionLogic = questionLogic;
    }

	public void setAttachmentsViewRenderer(AttachmentsViewRenderer attachmentsViewRenderer) {
		this.attachmentsViewRenderer = attachmentsViewRenderer;
	}
	
	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}
	
	private TargettedMessageList targettedMessageList;	
	public void setTargettedMessageList(TargettedMessageList targettedMessageList) {
		this.targettedMessageList = targettedMessageList;
	}
	/**
	 * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
	 */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {

		String answerLocator = "AnswerLocator";
		String questionLocator = "QuestionLocator";
		String optionsLocator = "OptionsLocator";
		String optionsOTP = optionsLocator + "." + optionsLogic.getOptionsForLocation(externalLogic.getCurrentLocationId()).getId();

		QuestionParams questionParams = (QuestionParams) viewparams;
		
		
		//can the user read this question?
		if (!permissionLogic.canRead(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			targettedMessageList.addMessage(new TargettedMessage("qna.warning.no-permission", new Object[]{questionParams.questionid}, TargettedMessage.SEVERITY_ERROR));
			return;
		}
		
		
		
		QnaQuestion question = questionLogic.getQuestionById(Long.valueOf(questionParams.questionid));

		navBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
		searchBarRenderer.makeSearchBar(tofill, "searchTool", VIEW_ID);
		
		//QNA-179 its possible the question doesn't exist
		if (question == null) {
			targettedMessageList.addMessage(new TargettedMessage("qna.warning.no-such-question", new Object[]{questionParams.questionid}, TargettedMessage.SEVERITY_ERROR));
			return;
		}
		
		
		
		if (!questionParams.direct) {
			questionIteratorRenderer.makeQuestionIterator(tofill, "iterator1:",question);
		}
		UIMessage.make(tofill,"page-title","qna.view-question.title");
		
		if (question.getCategory() != null) {
			UIOutput.make(tofill,"category-title",question.getCategory().getCategoryText());
		}
		
		UIMessage.make(tofill,"question-title","qna.view-question.question");

		UIVerbatim.make(tofill,"question",question.getQuestionText());

		// Render attachments
		if (question.getAttachments().size() > 0) {
			attachmentsViewRenderer.makeAttachmentsView(tofill, "attachmentsViewTool:", question); }

		String dateToDisplay = DateUtil.getSimpleDateTime(question.getDateLastModified());	
		
		UIMessage.make(tofill,"question-id","qna.view-question.id", new Object[] { question.getId() + ""});
		
		// If anonymous remove name
		if (question.isAnonymous()) {
			UIMessage.make(tofill,"question-submit-details","qna.view-question.submitter-detail-anonymous", new Object[] {dateToDisplay,question.getViews()});
		} else {
			UIMessage.make(tofill,"question-submit-details","qna.view-question.submitter-detail", new Object[] {externalLogic.getUserDisplayName(question.getOwnerId()),dateToDisplay,question.getViews()});
		}

		if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			UIInternalLink editLink = UIInternalLink.make(tofill, "edit-question-link", UIMessage.make("qna.view-question.edit"), new QuestionParams(EditPublishedQuestionProducer.VIEW_ID, question.getId().toString()));
			editLink.decorators = new DecoratorList(new UITooltipDecorator(UIMessage.make("qna.view-question.edit.tip")));
			
			UIInternalLink moveLink = UIInternalLink.make(tofill, "move-category-link", UIMessage.make("qna.view-question.move-category"), new QuestionParams(MoveQuestionProducer.VIEW_ID, question.getId().toString()));
			moveLink.decorators = new DecoratorList(new UITooltipDecorator(UIMessage.make("qna.view-question.move-category.tip")));
			
			UIInternalLink deleteLink = UIInternalLink.make(tofill, "delete-question-link", UIMessage.make("qna.general.delete"), new QuestionParams(DeleteQuestionProducer.VIEW_ID, question.getId().toString()));
			deleteLink.decorators = new DecoratorList(new UITooltipDecorator(UIMessage.make("qna.view-question.delete.tip")));
		}

		UIMessage.make(tofill,"answers-title","qna.view-question.answers-title",new Object[] {question.getAnswers().size()});

		renderAnswers(tofill, question, answerLocator);

		if (!questionParams.direct) {
			questionIteratorRenderer.makeQuestionIterator(tofill, "iterator2:",question);
		}

		if (permissionLogic.canAddNewAnswer(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
			String answerOTP = answerLocator + "." + AnswerLocator.NEW_1;

			UILink icon = UILink.make(tofill,"add-answer-icon","/library/image/silk/add.png");
			UILink link = UIInternalLink.make(tofill, "add-answer-link", UIMessage.make("qna.view-question.add-an-answer"), "");
			UIOutput div = UIOutput.make(tofill,"add-answer");

			UIInitBlock.make(tofill, "onclick-init", "init_add_answer_toggle", new Object[]{link,icon,div});

			UIForm form = UIForm.make(tofill,"add-answer-form");

			UIMessage.make(form,"add-answer-title","qna.view-question.add-your-answer");

			UIInput answertext = UIInput.make(form, "answer-input:",answerOTP + ".answerText");
	        richTextEvolver.evolveTextInput(answertext);

	        form.parameters.add(new UIELBinding(answerOTP + ".question", new ELReference(questionLocator + "." + question.getId())));

	        // If user has moderation rights automatically approve
	        if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
	        	form.addParameter(new UIELBinding(answerOTP + ".approved", true));
	        }

	        form.addParameter(new UIELBinding(answerOTP + ".privateReply", false));
	        form.addParameter(new UIELBinding(answerOTP + ".anonymous", new ELReference(optionsOTP + ".anonymousAllowed")));

	        UICommand.make(form,"add-answer-button",UIMessage.make("qna.view-question.add-answer"), answerLocator + ".saveAll");
	        UICommand.make(form,"cancel-button",UIMessage.make("qna.general.cancel")).setReturn("cancel");
		}

		// Increment views
		questionLogic.incrementView(question.getId());
	}

	/**
	 * Renders answers
	 * 
	 * @param tofill		{@link UIContainer} to fill
	 * @param question		{@link QnaQuestion}
	 * @param answerLocator AnswerLocator value
	 */
	private void renderAnswers(UIContainer tofill, QnaQuestion question, String answerLocator) {
		List<QnaAnswer> answers = question.getAnswers();
		Collections.sort(answers,new AnswersListComparator(permissionLogic,externalLogic));
		if (answers.size() == 0) {
			UIMessage.make(tofill,"no-answers", "qna.view-question.no-answers");
		} else {
			for (QnaAnswer qnaAnswer : answers) {
				UIBranchContainer answer = UIBranchContainer.make(tofill, "answer:");

				// Heading of answer
				if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), qnaAnswer.getOwnerId())) {
					UIOutput.make(answer, "answer-detail");
					UILink answerIcon = UILink.make(answer, "answer-icon","/library/image/silk/user_suit.png");
					DecoratorList dec = new DecoratorList(
							new UIAlternativeTextDecorator(UIMessage.make("qna.view-questions.officialpic.alt")));
					dec.add(new UITooltipDecorator(UIMessage.make("qna.view-questions.officialpic.alt")));
					answerIcon.decorators = dec;
					UIMessage.make(answer,"answer-heading","qna.view-question.lecturer-given-answer");
				} else if (qnaAnswer.isApproved()) {
					UIOutput.make(answer, "answer-detail");
					UILink answericon = UILink.make(answer, "answer-icon","/library/image/silk/accept.png");
					DecoratorList dec = new DecoratorList(new UIAlternativeTextDecorator(UIMessage.make("qna.view-questions.aprovedpic.alt")));
					dec.add(new UITooltipDecorator(UIMessage.make("qna.view-questions.aprovedpic.alt")));
					answericon.decorators = dec;
					
					UIMessage.make(answer,"answer-heading","qna.view-question.lecturer-approved-answer");
				}

				if (permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), externalLogic.getCurrentUserId())) {
					if (externalLogic.getCurrentUserId().equals(qnaAnswer.getOwnerId())) {
						UIInternalLink editLink = UIInternalLink.make(answer,"edit-answer-link",UIMessage.make("qna.view-question.edit"),new AnswerParams(EditPublishedAnswerProducer.VIEW_ID,qnaAnswer.getId(),question.getId()));
						editLink.decorators = new DecoratorList(new UITooltipDecorator(UIMessage.make("qna.view-question.edit-answer.tip")));
					} else if (qnaAnswer.isApproved() && !permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), qnaAnswer.getOwnerId())) {
						UILink link = UIInternalLink.make(answer,"withdraw-approval-link",UIMessage.make("qna.view-question.withdraw-approval"),new SimpleViewParameters(ViewQuestionProducer.VIEW_ID));
						UIForm form = UIForm.make(answer,"withdraw-approval-form");
						form.addParameter(new UIELBinding(answerLocator + "." + qnaAnswer.getId() + ".approved", false));
						UICommand command = UICommand.make(form,"withdraw-approval-command",answerLocator + ".withdrawApproval");
						UIInitBlock.make(answer, "make-link-submit", "make_link_call_command", new Object[]{link,command});
					} else if (!permissionLogic.canUpdate(externalLogic.getCurrentLocationId(), qnaAnswer.getOwnerId())) {
						UILink link = UILink.make(answer,"mark-correct-link",UIMessage.make("qna.view-question.mark-as-correct"),null);
						UIForm form = UIForm.make(answer,"mark-correct-form");
						form.addParameter(new UIELBinding(answerLocator + "." + qnaAnswer.getId() + ".approved", true));
						UICommand command = UICommand.make(form,"mark-correct-command",answerLocator + ".approve");
						UIInitBlock.make(answer, "make-link-submit", "make_link_call_command", new Object[]{link,command});
					}
					UIInternalLink deleteLink = UIInternalLink.make(answer,"delete-answer-link",UIMessage.make("qna.general.delete"),new AnswerParams(DeleteAnswerProducer.VIEW_ID,qnaAnswer.getId(),question.getId()));
					deleteLink.decorators = new DecoratorList(new UITooltipDecorator(UIMessage.make("qna.view-question.delete-answer.tip")));
				}

				UIVerbatim.make(answer, "answer-text", qnaAnswer.getAnswerText());
				UIMessage.make(answer, "answer-timestamp","qna.general.one-parameter",new Object[]{  DateUtil.getSimpleDateTime(qnaAnswer.getDateLastModified())});
			}
		}
	}

	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase("cancel",new SimpleViewParameters(QuestionsListProducer.VIEW_ID)));
		return list;
	}

	public ViewParameters getViewParameters() {
		return new QuestionParams();
	}

	public void interceptActionResult(ARIResult result, ViewParameters incoming, Object actionReturn) {
		if (result.resultingView instanceof QuestionParams) {
			QuestionParams params = (QuestionParams)result.resultingView;
			if (params.questionid == null) {
				params.questionid = ((QuestionParams)incoming).questionid; 
			}
		}
	}

}
