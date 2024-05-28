package gm.servicedesk.repository;

import gm.servicedesk.model.Org;
import gm.servicedesk.model.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByOrg(Org org);
}
