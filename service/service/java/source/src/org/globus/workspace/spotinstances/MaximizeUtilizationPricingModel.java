package org.globus.workspace.spotinstances;

import java.util.Collection;
import java.util.TreeSet;

public class MaximizeUtilizationPricingModel extends AbstractPricingModel {

    private boolean setMinPrice;

    public MaximizeUtilizationPricingModel(){
        this(true);
    }    
    
    public MaximizeUtilizationPricingModel(boolean setMinPrice){
        this.setMinPrice = setMinPrice;
    }
    
    @Override
    protected Double getNextPriceImpl(Integer totalReservedResources,
            Collection<SIRequest> requests, Double currentPrice) {
       
        TreeSet<SIRequest> orderedRequests = new TreeSet<SIRequest>(requests);
        
        Double nextPrice = PricingModelConstants.MINIMUM_PRICE;
        Integer availableResources = totalReservedResources;
        
        for (SIRequest siRequest : orderedRequests.descendingSet()) {
            Double maxBid = siRequest.getMaxBid();
            if(maxBid >= PricingModelConstants.MINIMUM_PRICE){
                nextPrice = siRequest.getMaxBid();
                availableResources -= siRequest.getNeededInstances();
                if(availableResources <= 0){
                    break;
                }
            }
        }
        
        if(availableResources > 0 && this.setMinPrice){
            nextPrice = PricingModelConstants.MINIMUM_PRICE;
        }
        
        return nextPrice;
    }


}