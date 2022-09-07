package com.bantads.account.lib;

public class TransferDetails {
    private String origin;
    private String destiny;

    public TransferDetails(String origin, String destiny) {
        this.origin = origin;
        this.destiny = destiny;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getTransferParticipant(){
        if (this.origin != null) {
            return this.origin;
        } else {
            return this.destiny;
        }
    }

    public void setTransferParticipant(String participant){
        if (this.origin != null) {
            this.origin = participant;
        } else {
            this.destiny = participant;
        }
    }
}