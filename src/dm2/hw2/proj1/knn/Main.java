package dm2.hw2.proj1.knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_all.data";
		String testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_all.data";
		List<String[]> queryList = new ArrayList<String[]>();
		
		
		KNN knn = new KNN();
		knn.initialize(5);
		
		try {
			knn.loadData(knn.dataList, trainFile);
			knn.loadData(queryList, testFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int error = 0;
		for(String[] fArray : queryList) {
			String r = knn.query(fArray);
			if (!(r.equals(fArray[0])))
				error++;
		}
		
		System.out.println("total:" + queryList.size());
		System.out.println("error:" + error);
		System.out.println("error rate:" + ((double)error / queryList.size()));
		System.out.println("acc rate:"+((double)(queryList.size() - error) / queryList.size()));
	}

}
