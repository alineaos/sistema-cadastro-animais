package shelter.animal.models.enums;

public enum PetType {
    GATO("Gato"),
    CACHORRO("Cachorro");

    private String classification;

    PetType(String classification){
        this.classification = classification;
    }

    public String getClassification(){
        return classification;
    }

    public static PetType selectType(String classification){
        for (PetType petType : PetType.values()){
            if(petType.classification.equalsIgnoreCase(classification)){
                return petType;
            }
        }
        return null;
    }
}
