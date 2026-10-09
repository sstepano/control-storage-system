package org.code_studio.model;

import java.util.List;

import org.code_studio.database.FileBankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileBankStatementModel extends JpaRepository <FileBankStatement, Integer> {

	List<FileBankStatement> findAllByFileHeaderId(Integer fileHeaderId);

}
