package com.wfsat.coopvoteapi.model;

public class VotoElegivel {

	 private boolean isValidCPF;
	    private String votoStatus;

	    public VotoElegivel() {
	    }

		public boolean isValidCPF() {
			return isValidCPF;
		}

		public void setValidCPF(boolean isValidCPF) {
			this.isValidCPF = isValidCPF;
		}

		public String getVotoStatus() {
			return votoStatus;
		}

		public void setVotoStatus(String votoStatus) {
			this.votoStatus = votoStatus;
		}

			

}
