package kernel.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class R_Exporter_Score{
	private String directryPath;
	private int time=0;
	//List<Pair<Integer, Integer>> buildingDamageScore
	public R_Exporter_Score() {
		this.directryPath=getDirectry();
	}
	//*********************************************************************************************************************************************************************************
	public void checkTime(){
		while(true) {
			String path=directryPath+"Time/"+(this.time+1);
			try{
				java.io.File file=new java.io.File(path);
				if(file.exists()){
					this.time+=1;
					continue;
				}else {
					break;
				}
			}catch (Exception e) {
				System.out.println("Exception"+e.getMessage());
			}
		}
	}
	//*********************************************************************************************************************************************************************************
	public String getDirectry() {
		try {
			String configPath="./config/Rione.cfg";
			File file=new File(configPath);
			if(file.exists()) {
				BufferedReader fileReader=new BufferedReader(new FileReader(file));
				String line=fileReader.readLine();
				while(line!=null) {
					String[] info=line.split(":");
					if(info!=null&&info.length==2) {
						switch (info[0]) {
						case "Directry":
							return info[1];
						}
					}
					line=fileReader.readLine();
				}
				fileReader.close();
			}
		} catch (Exception e) {}
		return new String();
	}
	//*********************************************************************************************************************************************************************************
	public void exportEachScoreLog(String scoreType,double score) {
		checkTime();
		try {
			String directry=directryPath+"Score";
			makeDirectry(directry);
			java.io.File file=new java.io.File(directry+"/"+this.time);
			if(file.createNewFile()||file.exists()) {
				FileWriter fileWriter=new FileWriter(file, true);
				String line=scoreType+":"+score+"\n";
				fileWriter.write(line);
				fileWriter.close();
			}
		}catch (IOException e) {System.out.println("IOException"+e.getMessage());}
	}
	//*********************************************************************************************************************************************************************************
	public java.io.File makeDirectry(String directryPath) {
		java.io.File directry=new java.io.File(directryPath);
		if(!directry.exists()) {directry.mkdirs();}
		return directry;
	}
}