package org.code_studio.model;

import org.code_studio.database.PaletteDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface PaletteDocumentModel extends JpaRepository <PaletteDocument, Integer> {

	@Transactional
	@Modifying
	@Query(value = """
	update PaletteDocument pd set
	pd.statusId = 5
	where 1=1
	  and pd.id = :documentId
	""", nativeQuery = false)
	Object markForOutput(Integer documentId);
	
	@Transactional
	@Modifying
	@Query(value = """
	update PaletteDocument pd set
	pd.statusId = 3
	where 1=1
	  and pd.id = :documentId
	""", nativeQuery = false)
	Object markForInput(Integer documentId);
	
}
