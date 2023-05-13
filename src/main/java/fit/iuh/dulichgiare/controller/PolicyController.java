package fit.iuh.dulichgiare.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fit.iuh.dulichgiare.dto.PolicyDTO;
import fit.iuh.dulichgiare.entity.Policy;
import fit.iuh.dulichgiare.service.PolicyService;

@RestController
@RequestMapping("/policys")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping(value = { "", "/" })
    public List<PolicyDTO> getAllPolicy() throws InterruptedException, ExecutionException {
        return policyService.getAllPolicy();
    }

    @GetMapping("/{id}")
    public PolicyDTO getPolicyById(@PathVariable long id) throws InterruptedException, ExecutionException {
        return policyService.getPolicyById(id);
    }

    @PostMapping(value = { "", "/save" })
    public Policy savePolicy(@RequestBody Policy policy) throws InterruptedException, ExecutionException {
        return policyService.savePolicy(policy);
    }

    @PostMapping(value = { "", "/update" })
    public int updatePolicy(@RequestBody Policy policy) throws InterruptedException, ExecutionException {
        return policyService.updatePolicy(policy);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePolicy(@PathVariable long id) throws InterruptedException, ExecutionException {
        return policyService.deletePolicy(id);
    }

    @GetMapping("/listName")
    public List<String> getAllPolicyByName() throws InterruptedException, ExecutionException {
        return policyService.getAllPolicyByName();
    }
}
