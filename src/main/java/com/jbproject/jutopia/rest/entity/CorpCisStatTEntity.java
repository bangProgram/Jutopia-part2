package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatTKey;
import com.jbproject.jutopia.rest.model.CorpCisStatModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class CorpCisStatTEntity extends BaseEntity implements Persistable<CorpCisStatTKey> {

    @EmbeddedId
    private CorpCisStatTKey id;

    private String stockName;
    private String accountName;

    @Override
    public CorpCisStatTKey getId() {
        return id;
    }
    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }
}
