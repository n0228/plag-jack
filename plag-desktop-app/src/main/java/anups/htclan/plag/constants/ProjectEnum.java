package anups.htclan.plag.constants;

public enum ProjectEnum {
	
	PROJECT_DIR_CURRENT(System.getProperty("user.dir")),
	PROJECT_TITLE_PLAGDESKAPP("plag-desktop-app"),
	PROJECT_TITLE_PLAGFILES("plag-files"),
	PROJECT_DIR_MAIN(PROJECT_DIR_CURRENT.value().replace(PROJECT_TITLE_PLAGDESKAPP.value(), ""));
	
    private String value;
    private ProjectEnum(String value){ this.value = value; }
	public String value() { return this.value; }
}
