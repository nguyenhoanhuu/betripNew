package fit.iuh.dulichgiare.dto;

import lombok.Data;

public class DestinationCount {
    private String destination;
    private long count;

    public DestinationCount(String destination, long count) {
        this.destination = destination;
        this.count = count;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}