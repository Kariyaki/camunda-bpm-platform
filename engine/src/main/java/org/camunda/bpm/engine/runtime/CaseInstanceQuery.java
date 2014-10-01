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
package org.camunda.bpm.engine.runtime;

import java.io.Serializable;

import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.query.Query;

/**
 * @author Roman Smirnov
 *
 */
public interface CaseInstanceQuery extends Query<CaseInstanceQuery, CaseInstance> {

  /**
   * Select the case instance with the given id
   *
   * @param caseInstanceId the id of the case instance
   *
   * @throws NotValidException when the given case instance id is null
   */
  CaseInstanceQuery caseInstanceId(String caseInstanceId);

  /**
   * Select case instances with the given business key
   *
   * @param caseInstanceBusinessKey the business key of the case instance
   *
   * @throws NotValidException when the given case instance business key is null
   */
  CaseInstanceQuery caseInstanceBusinessKey(String caseInstanceBusinessKey);

  /**
   * Select the case instances which are defined by a case definition with
   * the given key.
   *
   * @param caseDefinitionKey the key of the case definition
   *
   * @throws NotValidException when the given case definition key is null
   */
  CaseInstanceQuery caseDefinitionKey(String caseDefinitionKey);

  /**
   * Selects the case instances which are defined by a case definition
   * with the given id.
   *
   * @param caseDefinitionId the id of the case definition
   *
   * @throws NotValidException when the given case definition id is null
   */
  CaseInstanceQuery caseDefinitionId(String caseDefinitionId);

  /** Only select case instances which are active. **/
  CaseInstanceQuery active();

  /** Only select case instances which are completed. **/
  CaseInstanceQuery completed();

  /** Only select case instances which are terminated. **/
  CaseInstanceQuery terminated();

  /**
   * Only select cases instances which have a global variable with the given value. The type
   * of variable is determined based on the value, using types configured in
   * {@link ProcessEngineConfigurationImpl#getVariableTypes()}.
   *
   * Byte-arrays and {@link Serializable} objects (which are not primitive type wrappers)
   * are not supported.
   *
   * @param name the name of the variable, cannot be null
   * @param value the value of the variable
   *
   * @throws NotValidException when the given name is null
   */
  CaseInstanceQuery variableValueEquals(String name, Object value);

  /**
   * Only select cases instances which have a global variable with the given name, but
   * with a different value than the passed value.
   *
   * Byte-arrays and {@link Serializable} objects (which are not primitive type wrappers)
   * are not supported.
   *
   * @param name name of the variable, cannot be null
   * @param value the value of the variable
   *
   * @throws NotValidException when the given name is null
   *
   */
  CaseInstanceQuery variableValueNotEquals(String name, Object value);


  /**
   * Only select cases instances which have a global variable value greater than the passed value.
   *
   * Booleans, Byte-arrays and {@link Serializable} objects (which are not primitive type wrappers)
   * are not supported.
   *
   * @param name variable name, cannot be null
   * @param value variable value, cannot be null
   *
   * @throws NotValidException when the given name is null or a null-value or a boolean-value is used
   *
   */
  CaseInstanceQuery variableValueGreaterThan(String name, Object value);

  /**
   * Only select cases instances which have a global variable value greater than or equal to
   * the passed value.
   *
   * Booleans, Byte-arrays and {@link Serializable} objects (which
   * are not primitive type wrappers) are not supported.
   *
   * @param name variable name, cannot be null
   * @param value variable value, cannot be null
   *
   * @throws NotValidException when the given name is null or a null-value or a boolean-value is used
   *
   */
  CaseInstanceQuery variableValueGreaterThanOrEqual(String name, Object value);

  /**
   * Only select cases instances which have a global variable value less than the passed value.
   *
   * Booleans, Byte-arrays and {@link Serializable} objects (which are not primitive type wrappers)
   * are not supported.
   *
   * @param name variable name, cannot be null
   * @param value variable value, cannot be null
   *
   * @throws NotValidException when the given name is null or a null-value or a boolean-value is used
   *
   */
  CaseInstanceQuery variableValueLessThan(String name, Object value);

  /**
   * Only select cases instances which have a global variable value less than or equal to the passed value.
   *
   * Booleans, Byte-arrays and {@link Serializable} objects (which are not primitive type wrappers)
   * are not supported.
   *
   * @param name variable name, cannot be null
   * @param value variable value, cannot be null
   *
   * @throws NotValidException when the given name is null or a null-value or a boolean-value is used
   *
   */
  CaseInstanceQuery variableValueLessThanOrEqual(String name, Object value);

  /**
   * Only select cases instances which have a global variable value like the given value.
   * This can be used on string variables only.
   *
   * @param name variable name, cannot be null
   * @param value variable value, cannot be null. The string can include the
   *              wildcard character '%' to express like-strategy:
   *              starts with (string%), ends with (%string) or contains (%string%).
   *
   * @throws NotValidException when the given name is null or a null-value or a boolean-value is used
   *
   */
  CaseInstanceQuery variableValueLike(String name, String value);

  //ordering /////////////////////////////////////////////////////////////////

  /** Order by id (needs to be followed by {@link #asc()} or {@link #desc()}). */
  CaseInstanceQuery orderByCaseInstanceId();

  /** Order by case definition key (needs to be followed by {@link #asc()} or {@link #desc()}). */
  CaseInstanceQuery orderByCaseDefinitionKey();

  /** Order by case definition id (needs to be followed by {@link #asc()} or {@link #desc()}). */
  CaseInstanceQuery orderByCaseDefinitionId();

}
