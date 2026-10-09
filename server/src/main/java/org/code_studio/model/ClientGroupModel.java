package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientGroupModel extends JpaRepository <ClientGroup, Integer> {

	List <ClientGroup> findAllByIsSupplierGroup(Boolean isSupplierGroup);}
