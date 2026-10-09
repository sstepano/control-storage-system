package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientStoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientStoreImageModel extends JpaRepository <ClientStoreImage, Integer> {

	List <ClientStoreImage> findAllByClientId(Integer clientId);

}
