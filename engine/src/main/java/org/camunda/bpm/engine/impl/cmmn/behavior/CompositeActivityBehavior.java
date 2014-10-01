/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmmn.behavior;

import org.camunda.bpm.engine.impl.cmmn.execution.CmmnActivityExecution;


/**
 * @author Roman Smirnov
 *
 */
public interface CompositeActivityBehavior extends CmmnActivityBehavior {

  public void handleChildDisabled(CmmnActivityExecution execution, CmmnActivityExecution child);

  public void handleChildTermination(CmmnActivityExecution execution, CmmnActivityExecution child);

  public void handleChildSuspension(CmmnActivityExecution execution, CmmnActivityExecution child);

  public void handleChildCompletion(CmmnActivityExecution execution, CmmnActivityExecution child);

}
