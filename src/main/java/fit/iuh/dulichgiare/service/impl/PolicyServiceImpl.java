package fit.iuh.dulichgiare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.PolicyDTO;
import fit.iuh.dulichgiare.entity.Policy;
import fit.iuh.dulichgiare.repository.PolicyRepository;
import fit.iuh.dulichgiare.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepo;

    @Override
    public List<PolicyDTO> getAllPolicy() throws InterruptedException, ExecutionException {
        return policyRepo.findAll().stream().map(policy -> {
            PolicyDTO dto = new PolicyDTO();
            dto.setId(policy.getId());
            dto.setPolicyName(policy.getPolicyName());
            dto.setPriceincludes(policy.getPriceincludes());
            dto.setPricenotincluded(policy.getPricenotincluded());
            dto.setChildfare(policy.getChildfare());
            dto.setPaymentconditions(policy.getPaymentconditions());
            dto.setConditionregistering(policy.getConditionregistering());
            dto.setNotecancel(policy.getNotecancel());
            dto.setContact(policy.getContact());
            return dto;
        }).toList();

    }

    @Override
    public PolicyDTO getPolicyById(long id) throws InterruptedException, ExecutionException {
        Policy policy = policyRepo.findById(id).get();
        PolicyDTO dto = new PolicyDTO();
        dto.setId(policy.getId());
        dto.setPolicyName(policy.getPolicyName());
        dto.setPriceincludes(policy.getPriceincludes());
        dto.setPricenotincluded(policy.getPricenotincluded());
        dto.setChildfare(policy.getChildfare());
        dto.setPaymentconditions(policy.getPaymentconditions());
        dto.setConditionregistering(policy.getConditionregistering());
        dto.setNotecancel(policy.getNotecancel());
        dto.setContact(policy.getContact());
        return dto;
    }

    @Override
    public Policy savePolicy(Policy policy) throws InterruptedException, ExecutionException {
        return policyRepo.save(policy);
    }

    @Override
    public int updatePolicy(Policy policy) throws InterruptedException, ExecutionException {
        if (policy != null) {
            policyRepo.save(policy);
            return 0;
        }
        return 1;
    }

    @Override
    public String deletePolicy(long id) throws InterruptedException, ExecutionException {
        if (id > 0) {
            policyRepo.deleteById(id);
            return "delete success id - " + id;
        }
        return "Delete failed";
    }

    @Override
    public List<String> getAllPolicyByName() throws InterruptedException, ExecutionException {
        List<String> policyName = new ArrayList<>();
        List<Policy> policys = policyRepo.findAll();
        for (Policy policy : policys) {
            policyName.add(policy.getPolicyName());
        }
        return policyName;

    }

}
