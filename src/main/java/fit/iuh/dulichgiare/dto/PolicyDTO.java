package fit.iuh.dulichgiare.dto;

import lombok.Data;

@Data
public class PolicyDTO {
    private long id;
    private String policyName;
    private String priceincludes;
    private String pricenotincluded;
    private String childfare;
    private String paymentconditions;
    private String conditionregistering;
    private String notecancel;
    private String contact;
}
