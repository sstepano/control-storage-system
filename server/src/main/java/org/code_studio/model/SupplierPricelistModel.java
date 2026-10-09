package org.code_studio.model;

import org.code_studio.database.SupplierPricelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierPricelistModel extends JpaRepository <SupplierPricelist, Integer> {}
