package fr.iutvalence.info.dut.m2107.tiles;

import java.util.Comparator;

public class BehaviorComparator implements Comparator<TileBehavior> {

	@Override
	public int compare(TileBehavior o1, TileBehavior o2) {
		if (o1.priority > o2.priority)
			return -1;
		else if (o1.priority == o2.priority)
			return 0;
		else
			return 1;
	}
	
}
