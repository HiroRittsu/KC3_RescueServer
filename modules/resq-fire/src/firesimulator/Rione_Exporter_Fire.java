package firesimulator;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import firesimulator.Rione_ClassURN.classURN;
import firesimulator.world.AmbulanceCenter;
import firesimulator.world.Building;
import firesimulator.world.FireStation;
import firesimulator.world.PoliceOffice;
import firesimulator.world.Refuge;
import firesimulator.world.World;


public class Rione_Exporter_Fire{
	
	private String directryPath;
	private int time=0;
	private boolean isSetup=false;


	public Rione_Exporter_Fire() {
		this.directryPath=Rione_Utility.getDataPath();
	}
	
	public void checkTime(){
		while(Rione_Utility.isTimeExist(Rione_Utility.createPath(directryPath, "Time", String.valueOf(time+1), "bin"))) {
			time++;
		}
	}
	
	/******************************************************************************************************/

	public void exportBuildingLog(World world) {
		try {
	        checkTime();
	        if(!isSetup) {
				exportFirstLog(world);
				isSetup=true;
			}else {
				exportNormalLog(world);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportFirstLog(World world) throws Exception {
		try {
			Collection<Building> buildings=world.getBuildings();
			if(buildings!=null&&!buildings.isEmpty()) {
				File file=new File(Rione_Utility.createPath(directryPath, "Area", "Building", "bin"));
				file.createNewFile();
				Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
				for(Building building:buildings) {
					if(building!=null) {
						writer.write(getClassID(building));
						writer.write(building.getID());
						writer.write(building.getX());
						writer.write(building.getY());
						writer.write(building.getBuildingAreaGround());
						writer.write(building.getBurningTemp());
						writer.write(building.getCapacity());
						writer.write(building.getCode());
						writer.write(building.getConsum());
						writer.write(building.getEnergy());
						writer.write(building.getFieryness());
						writer.write(building.getFloors());
						writer.write(building.getFuel());
						writer.write(building.getIgnition());
						writer.write(building.getIgnitionPoint());
						writer.write(building.getInitialFuel());
						writer.write(building.getLastWater());
						writer.write(building.getPrevBurned());
						writer.write(building.getRadiationEnergy());
						writer.write(building.getTemperature());
						writer.write(building.getWaterQuantity());
						writer.write(building.getLastWatered());
						writer.write(building.getApexes());
					}
				}
				writer.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/******************************************************************************************************/

	public void exportNormalLog(World world) throws Exception {
		try {
			Collection<Building> buildings=world.getBuildings();
			if(buildings!=null&&!buildings.isEmpty()) {
				File file=new File(Rione_Utility.createPath(directryPath, "Area", "bu"+this.time, "bin"));
				file.createNewFile();
				Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
				for(Building building:buildings) {
					if(building!=null) {
						writer.write(building.getID());
						writer.write(building.getConsum());
						writer.write(building.getEnergy());
						writer.write(building.getFieryness());
						writer.write(building.getFuel());
						writer.write(building.getIgnition());
						writer.write(building.getIgnitionPoint());
						writer.write(building.getInitialFuel());
						writer.write(building.getLastWater());
						writer.write(building.getPrevBurned());
						writer.write(building.getRadiationEnergy());
						writer.write(building.getTemperature());
						writer.write(building.getWaterQuantity());
						writer.write(building.getLastWatered());
					}
				}
				writer.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/******************************************************************************************************/

	private int getClassID(Building building) {
		if(building instanceof Refuge) {
			return classURN.Refuge.getID();
		}else if(building instanceof AmbulanceCenter) {
			return classURN.AmbulanceCenter.getID();
		}else if(building instanceof FireStation) {
			return classURN.FireStation.getID();
		}else if(building instanceof PoliceOffice){
			return classURN.PoliceOffice.getID();
		}
		return classURN.Building.getID();
	}
	
}