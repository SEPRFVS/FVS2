package gameLogic.resource;

import gameLogic.Disposable;

public abstract class Resource implements Disposable {
	protected String name;

	@Override
	public String toString() {
		return name;
	}
}