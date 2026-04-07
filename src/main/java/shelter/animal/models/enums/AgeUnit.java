package shelter.animal.models.enums;

import shelter.animal.exceptions.BusinessException;

public enum AgeUnit {
    YEARS(1, "anos"),
    MONTH(2, "meses");

    private final int code;
    private final String label;

    AgeUnit(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static AgeUnit selectByCode(int codeInput){
        for (AgeUnit unit : AgeUnit.values()) {
            if(unit.getCode() == codeInput){
                return unit;
            }
        }
        throw new BusinessException("O código [%d] não existe.".formatted(codeInput));
    }

    public static AgeUnit selectByLabel(String labelInput){
        for (AgeUnit unit : AgeUnit.values()) {
            if(unit.getLabel().equalsIgnoreCase(labelInput)){
                return unit;
            }
        }
        return null;
    }
}
