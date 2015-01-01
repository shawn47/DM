package dm2.hw2.proj2.kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KMeans {
    public static boolean init;//判断是否是第一次，需要随机产生中心点
    public static int K;//kmeans关键参数
    public static List<Location> centers;//中心点集合
    public static List<Location> data;
    public int [] setInfo;
    public int [] cal = new int[11];
    public double purity = 0;
    public double recall = 0;
    public double fscore = 0;

    public KMeans(int K) {
    	this.K = K;
        data = initialize();
        init = false;
    }

    public void nextState() {
    	purity = 0;
        if(false == init) {
            centers = new ArrayList<Location>();
            for(int i = 1; i <= K; i++) {
                int index = new Random().nextInt(data.size());
                Location p = data.get(index);
                p.category = i;
                centers.add(p);
            }
            init = true;
            
        }
        Map<Integer,List<Integer> > categoryData = new HashMap<Integer,List<Integer> >();
        for(int i = 0; i < data.size(); i++) {
            int category = 0;
            double dist = Double.MAX_VALUE;
            for(int j = 0; j < K; j++) {
                double temp = distance(data.get(i),centers.get(j));
                if(temp < dist)
                {
                    dist = temp;
                    category = centers.get(j).category;
                }
            }
            data.get(i).category = category; 
            if(null == categoryData.get(category)) {
                List<Integer> l = new ArrayList<Integer>();
                l.add(i);
                categoryData.put(category, l);
            }
            else {
                 List<Integer> l = categoryData.get(category);
                 l.add(i);
                 categoryData.put(category, l);
            }
        }
        // update center point
        for(int key = 1; key <= K; key++) {
            List<Integer> indexes = categoryData.get(key);
            if(null != indexes) {
                double totalX = 0;
                double totalY = 0;
                for(Integer i: indexes) {
                    totalX += data.get(i).x;
                    totalY += data.get(i).y;
                }
                double avgX = totalX/indexes.size();
                double avgY = totalY/indexes.size();
                centers.get(key - 1).x = avgX;
                centers.get(key - 1).y = avgY;
            }
        }
        
        for (int key = 1; key <= K; key++) {
        	for (int i = 0; i < 11; i++) 
        		cal[i] = 0;
        	List<Integer> indexes = categoryData.get(key);
        	if (indexes != null) {
        		for (Integer i: indexes) {
        			switch (data.get(i).location) {
	        			case "46":
	                  		cal[0]++;
	                  		break;
	                  	case "11543":
	                  		cal[1]++;
	                  		break;
	                  	case "861":
	                  		cal[2]++;
	                  		break;
	                  	case "2501":
	                  		cal[3]++;
	                  		break;
	                  	case "93036":
	                  		cal[4]++;
	                  		break;
	                  	case "2333":
	                  		cal[5]++;
	                  		break;
	                  	case "209":
	                  		cal[6]++;
	                  		break;
	                  	case "74683":
	                  		cal[7]++;
	                  		break;
	                  	case "2226":
	                  		cal[8]++;
	                  		break;
	                  	case "843":
	                  		cal[9]++;
	                  		break;
	                  	case "13556":
	                  		cal[10]++;
	                  		break;
        			}
        		}
        	}
        	int max = 0;
    		for (int j = 0; j < cal.length; j++) {
    			if (cal[j] > max)
    				max = cal[j];
    		}
    		for (int i = 0; i < cal.length; i++)
    			if (cal[i] == max) {
    				purity += (double)max / (double)indexes.size();
    				// assume that precisoin is purity
    				recall += (double)max / (double)setInfo[i];
    				break;
    			}
    		
        }
        purity /= K;
        recall /= K;
        fscore = (2 * purity * recall) / (purity + recall);
        System.out.println("======================================");
        System.out.println("完成一次聚类!");
        System.out.println("purity \t "+purity);
        System.out.println("recall \t "+recall);
        System.out.println("f-score \t "+fscore);
    }
    //返回距离的平方和，故可以返回int
    private static double distance(Location p1,Location p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }
    //读取数据文件
    private List<Location> initialize() {
    	setInfo = new int[11];
    	for (int i = 0; i < 11; i++) 
    		setInfo[i] = 0;
        List<Location> data = new ArrayList<Location>();
        BufferedReader in = null;
        try {
            String fileName = "E:\\作业\\数据仓储\\2\\382108905_3_第二次作业\\第二次作业\\任务二\\data.txt";
            in = new BufferedReader(new FileReader(fileName));
            String line = null;
            while((line = in.readLine()) != null) {
              String[] fiveNumber = line.split("\t");
              double x = Double.parseDouble(fiveNumber[0]);
              double y = Double.parseDouble(fiveNumber[1]);
              String location = fiveNumber[2];
              switch (location) {
              	case "46":
              		setInfo[0]++;
              		break;
              	case "11543":
              		setInfo[1]++;
              		break;
              	case "861":
              		setInfo[2]++;
              		break;
              	case "2501":
              		setInfo[3]++;
              		break;
              	case "93036":
              		setInfo[4]++;
              		break;
              	case "2333":
              		setInfo[5]++;
              		break;
              	case "209":
              		setInfo[6]++;
              		break;
              	case "74683":
              		setInfo[7]++;
              		break;
              	case "2226":
              		setInfo[8]++;
              		break;
              	case "843":
              		setInfo[9]++;
              		break;
              	case "13556":
              		setInfo[10]++;
              		break;
              		
              }
            	
              //setInfo[Integer.parseInt(location)]++;
              data.add(new Location(x, y, location, 0));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(KMeans.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(KMeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public static void main(String[] args) {
    	KMeans kmeans = new KMeans(11);
    	kmeans.nextState();
    	kmeans.nextState();
    }
}
