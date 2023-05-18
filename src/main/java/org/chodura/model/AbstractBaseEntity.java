package org.chodura.model;

import java.util.UUID;

public abstract class AbstractBaseEntity implements BaseEntity {
    protected int id;

    protected UUID guid;

    protected AbstractBaseEntity(int id, UUID guid) {
        this.id = id;
        this.guid = guid;
    }

    protected AbstractBaseEntity() {
    }

    public int getId() {
        return id;
    }

    public UUID getGuid() {
        return guid;
    }


}
