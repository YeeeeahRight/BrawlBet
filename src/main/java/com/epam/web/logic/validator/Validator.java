package com.epam.web.logic.validator;

import com.epam.web.model.Entity;

/**
 * Interface as template of <T> object validator.
 *
 * @param  <T>  type of entities to validate.
 */
public interface Validator<T extends Entity> {

    /**
     * Validates given <T> object and returns boolean result.
     *
     * @param  entity  <T> object to validate.
     *
     * @return  boolean result of validation.
     */
    boolean isValid(T entity);
}
