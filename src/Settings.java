
public class Settings {
	public static int displayMode=0;
	public static int cctvImp=0;
	public static int shelterImp=0;
	public static int convenienceImp=0;
	public static int widthImp=0;
	public static int brightnessImp=0;
	public static int adultEntImp=0;
	public static int constructionImp=0;
	
	public static void update(int displayMode, int cctvImp, int shelterImp, int convenienceImp, int widthImp, int brightnessImp, int adultEntImp, int constructionImp) {
		Settings.displayMode = displayMode;
		Settings.cctvImp = cctvImp;
		Settings.shelterImp = shelterImp;
		Settings.convenienceImp = convenienceImp;
		Settings.widthImp = widthImp;
		Settings.brightnessImp = brightnessImp;
		Settings.adultEntImp = adultEntImp;
		Settings.constructionImp = constructionImp;
	}
}
