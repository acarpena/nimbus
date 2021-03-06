/*
 * Copyright 1999-2008 University of Chicago
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

package org.globus.workspace.client_core.actions;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.workspace.client_core.ExecutionProblem;
import org.globus.workspace.client_core.ParameterProblem;
import org.globus.workspace.client_core.StubConfigurator;
import org.globus.workspace.client_core.WSAction_Ensemble;
import org.globus.workspace.common.print.Print;
import org.globus.workspace.client_core.utils.RMIUtils;
import org.nimbustools.messaging.gt4_0.generated.ensemble.WorkspaceEnsemblePortType;
import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType;
import org.oasis.wsrf.lifetime.ResourceUnknownFaultType;

import java.rmi.RemoteException;

public class Destroy_Ensemble extends WSAction_Ensemble {

    
    // -------------------------------------------------------------------------
    // CONSTRUCTORS
    // -------------------------------------------------------------------------

    /**
     * @see WSAction_Ensemble
     */
    public Destroy_Ensemble(EndpointReferenceType epr,
                            StubConfigurator stubConf,
                            Print debug) {
        super(epr, stubConf, debug);
    }

    /**
     * @see WSAction_Ensemble
     */
    public Destroy_Ensemble(WorkspaceEnsemblePortType ensemblePortType,
                            Print debug) {
        super(ensemblePortType, debug);
    }

    
    // -------------------------------------------------------------------------
    // EXECUTE
    // -------------------------------------------------------------------------

    /**
     * Calls destroy()
     * 
     * @return null
     * @throws Exception see destroy()
     * @see #destroy()
     */
    protected Object action() throws Exception {
        this.destroy();
        return null;
    }

    /**
     * Destroy ensemble resource.
     *
     * @throws ParameterProblem validation problem
     * @throws ExecutionProblem general problem running (connection errors etc)
     * @throws ResourceNotDestroyedFaultType severe
     * @throws ResourceUnknownFaultType gone already
     */
    public void destroy() throws ParameterProblem,
                                 ExecutionProblem,
                                 ResourceUnknownFaultType,
                                 ResourceNotDestroyedFaultType {

        this.validateAll();

        try {
            
            ((WorkspaceEnsemblePortType) this.portType).
                    destroy(new Destroy());
            
        } catch (ResourceUnknownFaultType e) {
            throw e;
        } catch (ResourceNotDestroyedFaultType e) {
            throw e;
        } catch (RemoteException e) {
            throw RMIUtils.generalRemoteException(e);
        }
    }
}
