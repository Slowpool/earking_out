package org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators;

import java.util.LinkedList;
import java.util.List;

// `validatee` is not fictional word. it does exist
public abstract class ValidatorService<Validatee> {

    public abstract List<Error> gatherAllErrors(final Validatee obj);

    public ValidationResult validate(final Validatee obj) {
        var errors = gatherAllErrors(obj);
        var isValid = errors.size() == 0;
        return new ValidationResult(isValid, errors);
    }

    protected final List<Error> createErrorsList() {
        return new LinkedList<Error>();
    }

}
