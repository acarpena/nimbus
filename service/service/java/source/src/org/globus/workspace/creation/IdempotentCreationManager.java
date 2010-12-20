/*
 * Copyright 1999-2010 University of Chicago
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.globus.workspace.creation;

import edu.emory.mathcs.backport.java.util.concurrent.locks.Lock;
import org.globus.workspace.service.InstanceResource;
import org.nimbustools.api.services.rm.DoesNotExistException;
import org.nimbustools.api.services.rm.ManageException;

public interface IdempotentCreationManager {
    Lock getLock(String creatorID, String clientToken);

    IdempotentReservation getOrCreateReservation(String creatorID, String clientToken);

    void completeReservation(String creatorID, String clientToken, InstanceResource[] resources) throws DoesNotExistException;

    void removeReservation(String creatorID, String clientToken) throws ManageException;

}