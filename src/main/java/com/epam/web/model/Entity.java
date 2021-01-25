package com.epam.web.model;

import java.io.Serializable;

/**
 * Interface describes all Entities in DB.
 * It is used to eliminate the possibility of inserting entities
 * that don't implement it into the DB.
 */
public interface Entity extends Serializable {
    /**
     * Gets id of entity.
     *
     * @return entity id.
     */
    Long getId();
}
