package org.bozntouran.dao;

import org.bozntouran.entities.Receipt;

import java.util.List;
import java.util.Optional;

public interface ReceiptDao {

    boolean save(Receipt receipt);

    boolean update(Receipt receipt);

    boolean delete(int id);

    Optional<List<Receipt>> getReceipts();

    Optional<Receipt> getReceipt(int id);

}
