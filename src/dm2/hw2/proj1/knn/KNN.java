package dm2.hw2.proj1.knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNN {
	private int attr_num;
	private int k = 0;
	protected List<String[]> dataList;
	
	public void initialize(int k) {
		this.k = k;
		dataList = new ArrayList<String[]>();
	}
	
	public String query(String[] _qArray) {
		List<Pair<String, Double>> candidates = new ArrayList<Pair<String,Double>>();
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < this.dataList.size(); ++i) {
			String[] fArray = this.dataList.get(i);
			double sim = this.sim(fArray, _qArray, 1);
			candidates.add(new Pair<String, Double>(fArray[0], sim));
		}
		Collections.sort(candidates);
		for(int i = 0; i < this.k && i < candidates.size(); ++i) {
			result.add(candidates.get(i).key);
		}
		
		String rtnclass = getResult(result);
		
		return rtnclass;
	}
	
	public double sim(String[] _fArray1, String[] _fArray2, int _beginIndex) {
		double sum = 0.0;
		for(int i = _beginIndex; i < _fArray1.length; ++i) {
			double f1 = Double.parseDouble(_fArray1[i]);
			double f2 = Double.parseDouble(_fArray2[i]);
			sum += (f1 - f2) * (f1 - f2);
		}
		return (double) Math.sqrt(sum);
	}
	
	public void loadData(List<String[]> _list, String trainFile) throws IOException {
		_list.clear();
		File file = new File(trainFile);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	
        		// data preparation
                String[] arr = tempString.split(",");
            	_list.add(arr);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}

	public String getResult(List<String> result) {
		int [] count = new int[this.k];
		for (int i = 0; i < this.k; i++) {
			for (int j = 0; j < this.k; j++) {
				if (i != j) {
					if (result.get(i).equals(result.get(j)))
						count[i]++;
				}
			}
		}
		int max = 0;
		for (int i = 0; i < this.k; i++) {
			if (count[i] > max)
				max = count[i];
		}
		String resultclass = result.get(0);
		for (int i = 0; i < this.k; i++)
			if (count[i] == max)
				resultclass = result.get(i); 
		return resultclass;
	}
}
