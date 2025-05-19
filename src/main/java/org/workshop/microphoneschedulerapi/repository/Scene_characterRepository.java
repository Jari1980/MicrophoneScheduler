package org.workshop.microphoneschedulerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.workshop.microphoneschedulerapi.domain.entity.Scene_character;

@Repository
public interface Scene_characterRepository extends JpaRepository<Scene_character, Long> {
}
