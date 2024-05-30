package gm.servicedesk.repository;

import gm.servicedesk.model.Org;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepo extends JpaRepository<Org, Integer> {
    public Optional<Org> findByName(String name);
}
