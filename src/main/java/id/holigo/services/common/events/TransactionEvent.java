package id.holigo.services.common.events;

import id.holigo.services.common.model.TransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class TransactionEvent implements Serializable {
    static final Long serialVersionUID = -51235123L;

    private TransactionDto transactionDto;

    public TransactionEvent(TransactionDto transactionDto){
        this.transactionDto = transactionDto;
    }
}
