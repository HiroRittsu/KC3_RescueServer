package firesimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Rione_Utility {
	
	private final static String name = "RioneViewer3.sh";

	public static String getDataPath() {
		String defaultPath=System.getProperty("user.home")+"/git/RioneViewer3/Data/";
		if(isDefaultPath(defaultPath)) {
			return defaultPath;
		}
		File file=new File(System.getProperty("user.home"));
		String path=getPath(file, name).getAbsolutePath();
		path=path.substring(0, path.lastIndexOf("/")+1)+"Data/";
		return path;
	}
	
	private static boolean isDefaultPath(String path) {
		return new File(path).exists();
	}
	
	private static File getPath(File file, String name) {
		for(File f: file.listFiles()) {
			if(f.isDirectory()) {
				File t=getPath(f, name);
				if(t!=null) {
					return t;
				}
			}else if(f.getName().equals(name)){
				return f;
			}
		}
		return null;
	}
	
	
	public static boolean isTimeExist(String path) {
		return new File(path).exists();
	}
	
	public static String createPath(String path, String folder, String file, String extension) {
		return path+folder+"/"+file+"."+extension;
	}
	
	
	public static void debugError(String line) {
		System.out.println("ビューワーからの警告: "+line);
	}
	
	
	
	
	
}