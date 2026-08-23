package org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators;

import java.util.List;

public record ValidationResult(boolean isValid, List<Error> errors) {

}
