package traffic3.objects;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import rescuecore2.standard.entities.AmbulanceCentre;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.Edge;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.FireStation;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.Hydrant;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.PoliceOffice;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.Road;
import rescuecore2.worldmodel.EntityID;
import traffic3.manager.TrafficManager;
import traffic3.objects.Rione_ClassURN.classURN;



public class Rione_Exporter_Traffic{

	private String directryPath;
	private int time=0;
	private boolean isSetup=false;
	
	
	
	
	public Rione_Exporter_Traffic() {
		this.directryPath=Rione_Utility.getDataPath();
	}
	private void checkTime(){
		while(Rione_Utility.isTimeExist(Rione_Utility.createPath(directryPath, "Time", String.valueOf(time+1), "bin"))) {
			time++;
		}
	}

	/**********************************************************************************************/

	public void calcLog(TrafficManager manager) {

		try {
			checkTime();
			if(!isSetup) {
				exportFirstAgentLog(manager);
				exportFirstAreaLog(manager);
				isSetup=true;
			}else {
				exportAgentLog(manager);
				exportNormalRoadLog(manager);
				exportBlockadeLog(manager);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**********************************************************************************************/

	private void exportFirstAgentLog(TrafficManager manager) throws Exception {
		Collection<TrafficAgent> humans=manager.getAgents();
		if(humans!=null&&!humans.isEmpty()) {
			File file=new File(Rione_Utility.createPath(directryPath, "Human", "Human", "bin"));
			file.createNewFile();							
			Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
			for(TrafficAgent trafficAgent:humans) {
				Human human=trafficAgent.getHuman();
				if(human!=null&&human.isHPDefined()&&human.isXDefined()&&human.isYDefined()) {
					writer.write(getHumanID(human));
					writer.write(human.getID().getValue());
					writer.write(human.getX());
					writer.write(human.getY());
					writer.write(human.getHP());
					writer.write(human.getDamage());
					writer.write(human.getBuriedness());
					writer.write(human.getPosition().getValue());
					writer.write(human.getStamina());
					writer.write(getWater(human));
				}
			}
			writer.close();
		}
	}
	
	
	private void exportAgentLog(TrafficManager manager) throws Exception {
		Collection<TrafficAgent> humans=manager.getAgents();
		if(humans!=null&&!humans.isEmpty()) {
			File file=new File(Rione_Utility.createPath(directryPath, "Human", "hu"+this.time, "bin"));
			file.createNewFile();
			Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
			for(TrafficAgent trafficAgent:humans) {
				Human human=trafficAgent.getHuman();
				if(human!=null&&human.isHPDefined()&&human.isXDefined()&&human.isYDefined()) {
					
					writer.write(human.getID().getValue());
					writer.write(human.getX());
					writer.write(human.getY());
					writer.write(human.getHP());
					writer.write(human.getDamage());
					writer.write(human.getBuriedness());
					writer.write(human.getPosition().getValue());
					writer.write(human.getStamina());
					writer.write(getWater(human));
				}
			}
			writer.close();
		}
	}

	private void exportFirstAreaLog(TrafficManager manager) throws Exception{
		Collection<TrafficArea> areas=manager.getAreas();
		if(areas!=null&&!areas.isEmpty()) {
			File file=new File(Rione_Utility.createPath(directryPath, "Area", "Area", "bin"));
			file.createNewFile();
			Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
			for(TrafficArea trafficArea:areas) {
				Area area=trafficArea.getArea();
				if(area!=null&&area.isXDefined()&&area.isYDefined()) {

					List<EntityID>blockades=area.getBlockades();
					List<EntityID>neighbours=area.getNeighbours();
					
					writer.write(getAreaID(area));
					writer.write(area.getID().getValue());
					writer.write(area.getX());
					writer.write(area.getY());
					writer.write(area.getApexList());
					writer.write(toInt(neighbours));
					writer.write(toEdge(area.getEdges()));
					writer.write(toInt(blockades));

				}
			}
			writer.close();
		}
	}

	/**********************************************************************************************/

	private void exportNormalRoadLog(TrafficManager manager) throws Exception{
		try {
			Collection<TrafficArea> areas=manager.getAreas();
			if(areas!=null&&!areas.isEmpty()) {
				File file=new File(Rione_Utility.createPath(directryPath, "Area", "ro"+this.time, "bin"));
				file.createNewFile();
				Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
				for(TrafficArea trafficArea:areas) {
					Area area=trafficArea.getArea();
					if(area!=null&&area.isXDefined()&&area.isYDefined()&&area instanceof Road) {

						List<EntityID>blockades=area.getBlockades();
						writer.write(area.getID().getValue());
						writer.write(toInt(blockades));

					}
				}
				writer.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************/

	private void exportBlockadeLog(TrafficManager manager) throws Exception{
		try {
			Collection<TrafficBlockade> blockades=manager.getBlockades();
			if(blockades!=null&&!blockades.isEmpty()) {
				File file=new File(Rione_Utility.createPath(directryPath, "Area", "bl"+this.time, "bin"));
				file.createNewFile();
				Rione_BynaryWriter writer=new Rione_BynaryWriter(file);
				for(TrafficBlockade trafficBlockade:blockades) {
					Blockade blockade=trafficBlockade.getBlockade();
					if(blockade!=null&&blockade.isXDefined()&&blockade.isYDefined()&&blockade.isApexesDefined()&&blockade.isPositionDefined()) {

						writer.write(blockade.getID().getValue());
						writer.write(blockade.getX());
						writer.write(blockade.getY());
						writer.write(blockade.getPosition().getValue());
						writer.write(blockade.getRepairCost());
						writer.write(blockade.getApexes());

					}
				}
				writer.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************/

	public void exportTimeStepLog(Human human, List<Point2D.Double>points, List<Point2D.Double>accelerations) {
		try {
			checkTime();
			if(human!=null&&points!=null&&!points.isEmpty()) {
				File file=new File(Rione_Utility.createPath(directryPath, "Human", "ts"+this.time, "bin"));
				file.createNewFile();
				Rione_BynaryWriter writer=new Rione_BynaryWriter(file, true);

				writer.write(human.getID().getValue());
				writer.write(toDouble(points));
				writer.close();

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**********************************************************************************************/

	private double[] toDouble(List<Point2D.Double> list) {
		if(list!=null&&!list.isEmpty()) {
			double[] array=new double[list.size()*2];
			for(int i=0;i<array.length;i+=2) {
				array[i]=list.get(i/2).getX();
				array[i+1]=list.get(i/2).getY();
			}
			return array;
		}
		return null;
	}

	/**********************************************************************************************/

	private int[] toInt(List<EntityID> list) {
		if(list!=null&&!list.isEmpty()) {
			int[] array=new int[list.size()];
			for(int i=0;i<array.length;i++) {
				array[i]=list.get(i).getValue();
			}
			return array;
		}
		return null;
	}

	/**********************************************************************************************/

	private int[] toEdge(List<Edge> list) {
		if(list!=null&&!list.isEmpty()) {
			List<Edge> neighbours=new ArrayList<>();
			for(int i=0;i<list.size();i++) {
				Edge edge=list.get(i);
				if(edge.getNeighbour()!=null) {
					neighbours.add(edge);
				}
			}
			if(!neighbours.isEmpty()) {
				int[] array=new int[neighbours.size()*5];
				for(int i=0;i<array.length;i+=5) {
					Edge edge=neighbours.get(i/5);
					array[i]=edge.getStartX();
					array[i+1]=edge.getStartY();
					array[i+2]=edge.getEndX();
					array[i+3]=edge.getEndY();
					array[i+4]=edge.getNeighbour().getValue();
				}
				return array;
			}
			return null;
		}
		return null;
	}
	/**********************************************************************************************/

	private int getAreaID(Area area) {
		if(area instanceof Road) {
			if(area instanceof Hydrant){
				return classURN.Hydrant.getID();
			}
			return classURN.Road.getID();
		}else {
			if(area instanceof Refuge) {
				return classURN.Refuge.getID();
			}else if(area instanceof AmbulanceCentre) {
				return classURN.AmbulanceCenter.getID();
			}else if(area instanceof FireStation) {
				return classURN.FireStation.getID();
			}else if(area instanceof PoliceOffice){
				return classURN.PoliceOffice.getID();
			}
			return classURN.Building.getID();
		}
	}

	/**********************************************************************************************/

	private int getHumanID(Human human) {
		if(human instanceof Civilian) {
			return classURN.Civilian.getID();
		}else  if(human instanceof AmbulanceTeam){
			return classURN.AmbulanceTeam.getID();
		}else if(human instanceof FireBrigade) {
			return classURN.FireBrigade.getID();
		}else if(human instanceof PoliceForce) {
			return classURN.PoliceForce.getID();
		}
		return 0;
	}

	/**********************************************************************************************/

	private int getWater(Human human) {
		if(human instanceof FireBrigade) {
			return ((FireBrigade)human).getWater();
		}
		return 0;
	}

	/**********************************************************************************************/
	private int getID(EntityID id) {
		if(id!=null) {
			return id.getValue();
		}else {
			return -1;
		}
	}
}