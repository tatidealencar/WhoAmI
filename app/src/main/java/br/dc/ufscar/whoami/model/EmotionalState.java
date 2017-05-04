package br.dc.ufscar.whoami.model;

import java.io.Serializable;

public class EmotionalState implements Serializable {

	private static final long serialVersionUID = 1L;
	private String predicate;
	private String start;
	private String end;
	private String durability;
	private String group;

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDurability() {
		return durability;
	}

	public void setDurability(String durability) {
		this.durability = durability;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

}
