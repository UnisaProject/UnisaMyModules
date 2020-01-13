/**
 * Copyright 2005 Sakai Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.sakaiproject.evaluation.tool.renderers;

import java.util.Map;

import org.sakaiproject.evaluation.constant.EvalConstants;
import org.sakaiproject.evaluation.model.EvalScale;
import org.sakaiproject.evaluation.model.EvalTemplateItem;

import uk.org.ponder.rsf.components.UIBoundBoolean;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.components.UISelectChoice;
import uk.org.ponder.rsf.components.UISelectLabel;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.components.decorators.DecoratorList;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;

/**
 * This handles the rendering of multiple answer type items
 *
 * @author Aaron Zeckoski (aaronz@vt.edu)
 */
@SuppressWarnings("deprecation")
public class MultipleAnswerRenderer implements ItemRenderer {

   /**
    * This identifies the template component associated with this renderer
    */
   public static final String COMPONENT_ID = "render-multipleanswer-item:";

   /* (non-Javadoc)
    * @see org.sakaiproject.evaluation.tool.renderers.ItemRenderer#renderItem(uk.org.ponder.rsf.components.UIContainer, java.lang.String, org.sakaiproject.evaluation.model.EvalTemplateItem, int, boolean)
    */
   public UIJointContainer renderItem(UIContainer parent, String ID, String[] bindings, EvalTemplateItem templateItem, int displayNumber, boolean disabled, Map<String, Object> renderProperties) {
      UIJointContainer container = new UIJointContainer(parent, ID, COMPONENT_ID);

      if (displayNumber <= 0) displayNumber = 0;
      // this if is here because giving an RSF input control a null binding AND a null initial value causes a failure
      String[] initValue = null;
      if (bindings[0] == null) initValue = new String[] {};

      String naBinding = null;
      if (bindings.length > 1) {
         naBinding = bindings[1];
      }
      Boolean naInit = null;
      if (naBinding == null) naInit = Boolean.FALSE;

      EvalScale scale = templateItem.getItem().getScale();
      String[] scaleOptions = scale.getOptions();
      int optionCount = scaleOptions.length;
      String scaleValues[] = new String[optionCount];
      String scaleLabels[] = new String[optionCount];

      String scaleDisplaySetting = templateItem.getScaleDisplaySetting();
      boolean usesNA = templateItem.getUsesNA();

      if (EvalConstants.ITEM_SCALE_DISPLAY_FULL.equals(scaleDisplaySetting) ||
            EvalConstants.ITEM_SCALE_DISPLAY_VERTICAL.equals(scaleDisplaySetting)) {

         UIBranchContainer fullFirst = UIBranchContainer.make(container, "fullType:");
         if ( renderProperties.containsKey(ItemRenderer.EVAL_PROP_RENDER_INVALID) ) {
            fullFirst.decorate( new UIStyleDecorator("validFail") ); // must match the existing CSS class
         } else if ( renderProperties.containsKey(ItemRenderer.EVAL_PROP_ANSWER_REQUIRED) ) {
        	 fullFirst.decorate( new UIStyleDecorator("compulsory") ); // must match the existing CSS class
         }


         for (int count = 0; count < optionCount; count++) {
            scaleValues[count] = Integer.toString(count);
            scaleLabels[count] = scaleOptions[count];
         }

         UIOutput.make(fullFirst, "itemNum", displayNumber+"" );
         UIVerbatim.make(fullFirst, "itemText", templateItem.getItem().getItemText());

         // display next row
         UIBranchContainer fullRow = UIBranchContainer.make(container, "nextrow:", displayNumber+"");

         String containerId;
         if ( EvalConstants.ITEM_SCALE_DISPLAY_FULL.equals(scaleDisplaySetting) ) {
            containerId = "fullDisplay:";
         } else if ( EvalConstants.ITEM_SCALE_DISPLAY_VERTICAL.equals(scaleDisplaySetting) ) {
            containerId = "verticalDisplay:";
         } else {
            throw new RuntimeException("Invalid scaleDisplaySetting (this should not be possible): " + scaleDisplaySetting);
         }

         UIBranchContainer displayContainer = UIBranchContainer.make(fullRow, containerId);

         UISelect radios = UISelect.makeMultiple(displayContainer, "dummyCheckbox", scaleValues, scaleLabels, bindings[0], initValue);
         String selectID = radios.getFullID();

         if (disabled) {
            radios.selection.willinput = false;
            radios.selection.fossilize = false;
         }

         for (int j = 0; j < scaleValues.length; ++j) {
            UIBranchContainer radiobranchNested =
               UIBranchContainer.make(displayContainer, "scaleOptions:", j+"");
            UISelectChoice choice = UISelectChoice.make(radiobranchNested, "choiceValue", selectID, j);
            UISelectLabel.make(radiobranchNested, "choiceLabel", selectID, j);		//.decorate( new UILabelTargetDecorator(choice) );

         }

         if (usesNA) {
            UIBranchContainer branchNA = UIBranchContainer.make(container, "showNA:");
            branchNA.decorators = new DecoratorList( new UIStyleDecorator("na") ); // must match the existing CSS class
            UIBoundBoolean checkbox = UIBoundBoolean.make(branchNA, "itemNA", naBinding, naInit);
            UIMessage.make(branchNA, "descNA", "viewitem.na.desc"); //.decorate( new UILabelTargetDecorator(checkbox) );
         }

      } else {
         throw new IllegalStateException("Unknown scaleDisplaySetting ("+scaleDisplaySetting+") for " + getRenderType());
      }

      // render the item comment
      ItemRendererImpl.renderCommentBlock(container, templateItem, bindings);

      return container;
   }

   /* (non-Javadoc)
    * @see org.sakaiproject.evaluation.tool.renderers.ItemRenderer#getRenderType()
    */
   public String getRenderType() {
      return EvalConstants.ITEM_TYPE_MULTIPLEANSWER;
   }

}
