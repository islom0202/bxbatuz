package org.example.bxbatuz.Repo;

import org.example.bxbatuz.Entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepo extends JpaRepository<Files, Long> {
}
