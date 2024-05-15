package gm.servicedesk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gm.servicedesk.dto.OrgAddReq;
import gm.servicedesk.model.Org;
import gm.servicedesk.repository.OrgRepo;

@Service
public class OrgService {

    static final Logger log = LoggerFactory.getLogger(OrgService.class);
    private final OrgRepo repo;

    public OrgService(OrgRepo repo) {
        this.repo = repo;
    }

    public List<Org> findAll() {
        return repo.findAll();
    }

    public Org addOrg(OrgAddReq req) {
        Org org = new Org();
        org.setName(req.name());
        return repo.save(org);
    }

    public void deleteOrg(Integer id) {
        repo.deleteById(id);
    }
}
