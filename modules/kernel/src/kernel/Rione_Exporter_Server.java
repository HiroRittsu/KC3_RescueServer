package kernel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import rescuecore2.config.Config;

public class Rione_Exporter_Server{
	
	private String directryPath;
	private int time=0;

	public Rione_Exporter_Server() {
		this.directryPath=Rione_Utility.getDataPath();
	}
	
	
	public void exportConfig(Config config) {
		try {
			File file=new File(Rione_Utility.createPath(this.directryPath, "World", "config", "cfg"));
			file.createNewFile();
			BufferedWriter writer=new BufferedWriter(new FileWriter(file));
			Map<String, String> map=config.getData();
			for(String key:map.keySet()) {
				writer.write(key+":"+map.get(key));
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public void setTime(int time){
		this.time=time;
		String path=directryPath+"Time/"+this.time+".bin";
		try{
			java.io.File file=new java.io.File(path);
			if(!file.exists()){
				file.createNewFile();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleateOldLog() {
		java.io.File agentLog=new java.io.File(directryPath+"Human");
		deleteFileAndContainsDirectry(agentLog);
		
		java.io.File sourceLog=new java.io.File(directryPath+"Source");
		deleteFileAndContainsDirectry(sourceLog);

		java.io.File areaData=new java.io.File(directryPath+"Area");
		deleteFileAndContainsDirectry(areaData);

		java.io.File userData=new java.io.File(directryPath+"UserData");
		deleteFileAndContainsDirectry(userData);

		java.io.File timeData=new java.io.File(directryPath+"Time");
		deleteFileAndContainsDirectry(timeData);

		java.io.File worldData=new java.io.File(directryPath+"World");
		deleteFileAndContainsDirectry(worldData);

		java.io.File scoreData=new java.io.File(directryPath+"Score");
		deleteFileAndContainsDirectry(scoreData);
	}
	
	private void deleteFileAndDirectry(java.io.File file) {
		if(file.exists()) {
			if(file.isDirectory()) {
				java.io.File[] files=file.listFiles();
				for(int i=0;i<files.length;i++) {
					if(files[i].exists()) {
						deleteFileAndDirectry(files[i]);
					}
				}
				file.delete();
			}else if(file.isFile()){
				file.delete();
			}
		}
	}
	/************************************************************************************/
	/**
	 * 指定のディレクトリ内のディレクトリはすべて残しファイルのみをすべて消去
	 * @param file
	 * @author 兼近
	 */
	private void deleteFileNotDirectry(java.io.File file) {
		if(file.exists()) {
			if(file.isDirectory()) {
				java.io.File[] files=file.listFiles();
				for(int i=0;i<files.length;i++) {
					if(files[i].exists()) {
						deleteFileNotDirectry(files[i]);
					}
				}
			}else if(file.isFile()){
				file.delete();
			}
		}
	}

	/************************************************************************************/
	/**
	 * 指定のディレクトリは残すが中のディレクトリとファイルはすべて消去
	 * @param file
	 * @author 兼近
	 */
	private void deleteFileAndContainsDirectry(java.io.File file) {
		if(file.exists()) {
			if(file.isDirectory()) {
				java.io.File[] files=file.listFiles();
				for(int i=0;i<files.length;i++) {
					if(files[i].exists()) {
						deleteFileAndDirectry(files[i]);
					}
				}
			}else if(file.isFile()){
				file.delete();
			}
		}
	}
}