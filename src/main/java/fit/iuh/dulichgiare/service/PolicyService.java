package fit.iuh.dulichgiare.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import fit.iuh.dulichgiare.dto.PolicyDTO;
import fit.iuh.dulichgiare.entity.Policy;

@Service
public interface PolicyService {

    public List<PolicyDTO> getAllPolicy() throws InterruptedException, ExecutionException;

    public PolicyDTO getPolicyById(long id) throws InterruptedException, ExecutionException;

    public Policy savePolicy(Policy policy) throws InterruptedException, ExecutionException;

    public int updatePolicy(Policy policy) throws InterruptedException, ExecutionException;

    public String deletePolicy(long id) throws InterruptedException, ExecutionException;

    public List<String> getAllPolicyByName() throws InterruptedException, ExecutionException;
}
