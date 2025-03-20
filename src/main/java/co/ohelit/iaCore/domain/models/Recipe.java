package co.ohelit.iaCore.domain.models;

public class Recipe {
    private Long id;
    private String code;
    private String configuration;
    private String description;
    private String automation;

    //Constructor
    public Recipe(){};
    Recipe(Long id, String code, String configuration, String description, String automation){
        this.id = id;
        this.code = code;
        this.configuration = configuration;
        this.description = description;
        this.automation = automation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAutomation() {
        return automation;
    }

    public void setAutomation(String automation) {
        this.automation = automation;
    }

}
