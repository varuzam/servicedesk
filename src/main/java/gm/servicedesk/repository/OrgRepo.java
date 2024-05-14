package gm.servicedesk.repository;

import gm.servicedesk.model.Org;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepo extends JpaRepository<Org, Integer> {
    public Org findIdByName(String name);
}
