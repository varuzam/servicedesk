package gm.servicedesk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gm.servicedesk.dto.TicketAddReq;
import gm.servicedesk.exception.*;
import gm.servicedesk.model.Org;
import gm.servicedesk.model.Ticket;
import gm.servicedesk.model.TicketStatus;
import gm.servicedesk.repository.TicketRepo;

@Service
public class TicketService {

    static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepo repo;

    public TicketService(TicketRepo repo) {
        this.repo = repo;
    }

    public List<Ticket> findAll() {
        return repo.findAll();
    }

    public List<Ticket> findAll(Org org) {
        return repo.findByOrg(org);
    }

    public Ticket find(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));
    }

    public Ticket add(TicketAddReq req, Org org) {
        Ticket t = new Ticket();
        t.setSubject(req.subject());
        t.setDesc(req.desc());
        t.setPriority(req.priority());
        t.setOrg(org);
        t.setStatus(TicketStatus.ACTIVE);
        return repo.save(t);
    }

}
