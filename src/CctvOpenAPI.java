import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CctvOpenAPI {
	
	final public static double latLowerBound = 37.5829796;
	final public static double latUpperBound = 37.5852749;

	final public static double longLowerBound = 127.0285901;
	final public static double longUpperBound = 127.031081;
	
	final public static double toWidth = 686;
	final public static double toHeight = 775;

	public static ArrayList<int[]> getCCTVCoordInMap() {
		ArrayList<int[]> cctvList = new ArrayList<>();
		InputStream is = null;
		try {
			URL url = new URL(
					"https://www.data.go.kr/dataset/fileDownload.do?atchFileId=FILE_000000001488659&fileDetailSn=1&publicDataDetailPk=uddi:384d9c68-44de-4623-8c73-ef43bb9f678a");
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "euc-kr");
			CSVParser csvParser = new CSVParser(isr, CSVFormat.DEFAULT);
			for (CSVRecord csvRecord : csvParser) {
				if (csvRecord.getRecordNumber() == 1)
					continue;
				double latitude = Double.parseDouble(csvRecord.get(10));
				double longtitude = Double.parseDouble(csvRecord.get(11));
				if(latitude>=latLowerBound && latitude<=latUpperBound && longtitude>=longLowerBound && longtitude<=longUpperBound) {
					int[] coord = new int[2];
					coord[0] = (int)Math.floor(toHeight - (latitude-latLowerBound)*toHeight/(latUpperBound-latLowerBound));
					coord[1] = (int)Math.floor((longtitude-longLowerBound)*toWidth/(longUpperBound-longLowerBound));
					cctvList.add(coord);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cctvList;
	}
}
