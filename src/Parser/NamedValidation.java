package Parser;

import java.util.function.Supplier;

class NamedValidation {
    private final String name;
    private final Supplier<Boolean> validationFunction;

    public NamedValidation(String name, Supplier<Boolean> validationFunction) {
        this.name = name;
        this.validationFunction = validationFunction;
    }

    public String getName() {
        return name;
    }

    public Supplier<Boolean> getValidationFunction() {
        return validationFunction;
    }
}

