package org.code_studio.model;

import org.code_studio.database.ItemBarcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBarcodeModel extends JpaRepository <ItemBarcode, Integer> {}
