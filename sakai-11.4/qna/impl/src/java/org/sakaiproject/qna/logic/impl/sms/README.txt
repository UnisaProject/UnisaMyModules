ABOUT SMS COMMANDS
========================
By implementing SmsCommand, users can extend the sms functionality of Sakai. Each command must have a unique command key. 
The key is used to communicate with the command via Sms. For example if the command key is QUESTION, then a user may send a message with body: 
QUESTION <siteid> <param1> <param2>. The command will also be recognised with QSTN, QUEST, or simply Q. 
This is part of the command matching algorithm of the sms command parser. Your command must return a validation string or an answer 
in the execute method. This string will be returned back to the user via a sms.  A standard help message will be returned if the user only 
sent the command without any siteid or parameters.

REGISTERING SMS COMMANDS
========================
Each command must be register using our SmsCommandRegisterHelper. For example:

<bean id="QnaCommandRegister"
		class="org.sakaiproject.sms.logic.incoming.helper.SmsCommandRegisterHelper" init-method="init" >
		<property name="incomingLogicManager" ref="org.sakaiproject.sms.logic.incoming.SmsIncomingLogicManager" />
		<property name="toolKey" value="qna" />
		<property name="commands">
			<list>
				<bean class="org.sakaiproject.qna.logic.impl.sms.QuestionSmsCommand">
					<property name="questionLogic" ref="org.sakaiproject.qna.logic.QuestionLogic" />
					<property name="optionsLogic" ref="org.sakaiproject.qna.logic.OptionsLogic" />
					<property name="qnaBundleLogic" ref="org.sakaiproject.qna.logic.QnaBundleLogic" />
					<property name="categoryLogic" ref="org.sakaiproject.qna.logic.CategoryLogic" />
				</bean>
				<bean class="org.sakaiproject.qna.logic.impl.sms.AnswerSmsCommand">
					<property name="questionLogic" ref="org.sakaiproject.qna.logic.QuestionLogic" />
					<property name="qnaBundleLogic" ref="org.sakaiproject.qna.logic.QnaBundleLogic" />
					<property name="permissionLogic" ref="org.sakaiproject.qna.logic.PermissionLogic" />			
					<property name="optionsLogic" ref="org.sakaiproject.qna.logic.OptionsLogic" />	
				</bean>
				<bean class="org.sakaiproject.qna.logic.impl.sms.ReplySmsCommand">
					<property name="questionLogic" ref="org.sakaiproject.qna.logic.QuestionLogic" />
					<property name="qnaBundleLogic" ref="org.sakaiproject.qna.logic.QnaBundleLogic" />	
					<property name="answerLogic" ref="org.sakaiproject.qna.logic.AnswerLogic" />	
					<property name="optionsLogic" ref="org.sakaiproject.qna.logic.OptionsLogic" />		
				</bean>
			</list>
		</property>
	</bean>	