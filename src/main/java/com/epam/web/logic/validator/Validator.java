package com.epam.web.logic.validator;

import com.epam.web.model.Entity;

public interface Validator<T extends Entity> {
    boolean isValid(T entity);
}
