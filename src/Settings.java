
public class Settings {
	private static int displayMode=0;
	private static int reputationImp=0;
	private static int cctvImp=0;
	private static int shelterImp=0;
	private static int convenienceImp=0;
	private static int widthImp=0;
	private static int brightnessImp=0;
	private static int adultEntImp=0;
	private static int constructionImp=0;
	
	public static void update(int displayMode, int reputationImp, int cctvImp, int shelterImp, int convenienceImp, int widthImp, int brightnessImp, int adultEntImp, int constructionImp) {
		Settings.setDisplayMode(displayMode);
		Settings.setReputationImp(reputationImp);
		Settings.setCctvImp(cctvImp);
		Settings.setShelterImp(shelterImp);
		Settings.setConvenienceImp(convenienceImp);
		Settings.setWidthImp(widthImp);
		Settings.setBrightnessImp(brightnessImp);
		Settings.setAdultEntImp(adultEntImp);
		Settings.setConstructionImp(constructionImp);
	}

	public static int getDisplayMode() {
		return displayMode;
	}

	public static void setDisplayMode(int displayMode) {
		Settings.displayMode = displayMode;
	}

	public static int getReputationImp() {
		return reputationImp;
	}

	public static void setReputationImp(int reputationImp) {
		Settings.reputationImp = reputationImp;
	}
	
	public static int getCctvImp() {
		return cctvImp;
	}

	public static void setCctvImp(int cctvImp) {
		Settings.cctvImp = cctvImp;
	}

	public static int getShelterImp() {
		return shelterImp;
	}

	public static void setShelterImp(int shelterImp) {
		Settings.shelterImp = shelterImp;
	}

	public static int getConvenienceImp() {
		return convenienceImp;
	}

	public static void setConvenienceImp(int convenienceImp) {
		Settings.convenienceImp = convenienceImp;
	}

	public static int getWidthImp() {
		return widthImp;
	}

	public static void setWidthImp(int widthImp) {
		Settings.widthImp = widthImp;
	}

	public static int getBrightnessImp() {
		return brightnessImp;
	}

	public static void setBrightnessImp(int brightnessImp) {
		Settings.brightnessImp = brightnessImp;
	}

	public static int getAdultEntImp() {
		return adultEntImp;
	}

	public static void setAdultEntImp(int adultEntImp) {
		Settings.adultEntImp = adultEntImp;
	}

	public static int getConstructionImp() {
		return constructionImp;
	}

	public static void setConstructionImp(int constructionImp) {
		Settings.constructionImp = constructionImp;
	}
}
