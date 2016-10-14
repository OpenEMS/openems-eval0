package io.openems.impl.controller;

import io.openems.api.controller.DataMap;
import io.openems.api.controller.Range;
import io.openems.api.general.data.IntegerValue;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;

public class EssContainer implements DataMap {
	
	@IsRequired(itemId = "Soc",permission = Permission.READ)
	public Range soc;
	
	@IsRequired(itemId = "ActivePower",permission = Permission.WRITE)
	public Range activePower;
	
	@IsRequired(itemId = "Running",permission = Permission.WRITE)
	public Range isRunning;
	
	@IsRequired(itemId = "OnGrid",permission = Permission.READ)
	public Range isOnGrid;
	
	@IsRequired(itemId = "minSoc",permission = Permission.READ)
	public Range minSoc;
	
	public boolean isOnGrid(){
		return isOnGrid.getReadValue().getValue().asBoolean();
	}
	
	public boolean isRunning(){
		return isRunning.getReadValue().getValue().asBoolean();
	}
	
	public void setActivePower(int power){
		activePower.setWriteValue(new IntegerValue(power));
	}
	
	public int getActivePower(){
		return activePower.getReadValue().getValue().asInt();
	}
	
	public int getUseableSoc(){
		return soc.getReadValue().getValue().asInt()-minSoc.getReadValue().getValue().asInt();
	}
}
