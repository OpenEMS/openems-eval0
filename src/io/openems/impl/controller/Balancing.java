package io.openems.impl.controller;

import java.util.Collections;
import java.util.List;

import io.openems.api.controller.Controller;

public class Balancing extends Controller {

	public List<EssContainer> ess;

	public List<MeterContainer> meter;

	public Balancing(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		if (isOnGrid()) {
			int calculatedPower = meter.get(0).getActivePower();
			int maxChargePower = 0;
			int maxDischargePower = 0;
			int useableSoc = 0;
			for (EssContainer e : ess) {
				calculatedPower += e.getActivePower();
				maxChargePower += e.activePower.getMinWriteValue().asInt();
				maxDischargePower += e.activePower.getMaxWriteValue().asInt();
				useableSoc += e.getUseableSoc();
			}
			if (calculatedPower > 0) {
				if (calculatedPower > maxDischargePower) {
					calculatedPower = maxChargePower;
				}
				Collections.sort(ess, (a, b) -> a.getUseableSoc() - b.getUseableSoc());
				for (int i = 0; i < ess.size(); i++) {
					EssContainer e = ess.get(i);
					int minP = calculatedPower;
					for (int j = i + 1; j < ess.size(); j++) {
						if (ess.get(j).getUseableSoc() > 0) {
							minP -= ess.get(j).activePower.getMinWriteValue().asInt();
						}
					}
					if (minP < 0) {
						minP = 0;
					}
					int maxP = e.activePower.getMinWriteValue().asInt();
					if (calculatedPower < maxP) {
						maxP = calculatedPower;
					}
					double diff = maxP - minP;
					// if (e.getUseableSoc() >= 0) {
					int p = (int) Math.ceil((minP + diff / useableSoc * e.getUseableSoc()) / 100) * 100;
					e.setActivePower(p);
					calculatedPower -= p;
					// }
				}
			} else {
				if (calculatedPower < maxChargePower) {
					calculatedPower = maxChargePower;
				}
				Collections.sort(ess, (a, b) -> (100 - a.getUseableSoc()) - (100 - b.getUseableSoc()));
				for (int i = 0; i < ess.size(); i++) {
					EssContainer e = ess.get(i);
					int minP = calculatedPower;
					for (int j = i + 1; j < ess.size(); j++) {
						minP -= ess.get(j).activePower.getMinWriteValue().asInt();
					}
					if (minP > 0) {
						minP = 0;
					}
					int maxP = e.activePower.getMinWriteValue().asInt();
					if (calculatedPower > maxP) {
						maxP = calculatedPower;
					}
					double diff = maxP - minP;
					int p = (int) Math.floor((minP + diff / useableSoc * (100 - e.getUseableSoc())) / 100) * 100;
					e.setActivePower(p);
					calculatedPower -= p;
				}
			}
		}
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 10;
	}

	public boolean isOnGrid() {
		for (EssContainer e : ess) {
			if (!e.isOnGrid()) {
				return false;
			}
		}
		return true;
	}

}
