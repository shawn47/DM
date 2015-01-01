package dm2.hw2.proj1.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class SVMII 
{
	private int exampleNum;
	private int exampleDim;
	private double[] w;
	private double lambda;
	private double lr = 0.001;
	private double threshold = 0.001;
	private double cost;
	private double[] grad;
	private double[] yp;
	public SVMII(double paramLambda) {
		lambda = paramLambda;	
	}
	
	private void CostAndGrad(double[][] X,double[] y) {
		cost = 0;
		for(int m = 0;m < exampleNum; m++) {
			yp[m] = 0;
			for(int d = 0;d < exampleDim; d++) {
				yp[m] += X[m][d] * w[d];
			}
			if(y[m] * yp[m] - 1 < 0) {
				cost += (1 - y[m] * yp[m]);
			}
		}
		
		for(int d = 0;d < exampleDim; d++) {
			cost += 0.5 * lambda * w[d] * w[d];
		}
		
		for(int d = 0; d < exampleDim; d++) {
			grad[d] = Math.abs(lambda * w[d]);	
			for(int m = 0; m < exampleNum; m++) {
				if(y[m] * yp[m]-1 < 0) {
					grad[d] -= y[m] * X[m][d];
				}
			}
		}				
	}
	
	private void update()
	{
		for(int d = 0;d < exampleDim; d++) {
			w[d] -= lr * grad[d];
		}
	}
	
	public void Train(double[][] X,double[] y,int maxIters)
	{
		exampleNum = X.length;
		if(exampleNum <=0) {
			System.out.println("num of example <=0!");
			return;
		}
		exampleDim = X[0].length;
		w = new double[exampleDim];
		grad = new double[exampleDim];
		yp = new double[exampleNum];
		
		for(int iter = 0; iter < maxIters; iter++) {
			
			CostAndGrad(X,y);
			System.out.println("cost:"+cost);
			if(cost< threshold) {
				break;
			}
			update();
			
		}
	}
	public int predict(double[] x) {
		double pre = 0;
		for(int j = 0; j < x.length; j++) {
			pre += x[j] * w[j];
		}
		if(pre >= 0)
			return 1;
		else return -1;
	}
	
	public void Test(double[][] testX,double[] testY)
	{
		int error=0;
		for(int i=0;i<testX.length;i++) {
			if (predict(testX[i]) != testY[i]) {
				error++;
			}
		}
		System.out.println("total:"+testX.length);
		System.out.println("error:"+error);
		System.out.println("error rate:"+((double)error/testX.length));
		System.out.println("acc rate:"+((double)(testX.length-error)/testX.length));
	}
	
	
	
	public void loadData(double[][]X,double[] y,String trainFile, String label) throws IOException {
		File file = new File(trainFile);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
            	
        		// data preparation
                String[] arr = tempString.split(",");
                double[] arr1 = new double[arr.length];
            	for (int i = 1; i < arr.length;i++)  {
                	arr1[i] = Double.parseDouble(arr[i]);
                	X[line - 1][i] = Double.parseDouble(arr[i]);
            	}
            	if (arr[0].equals(label)) {
        			arr1[0] = 1.0;
        			y[line - 1] = 1.0;
            	}
            	else {
            		arr1[0] = -1.0;
            		y[line - 1] = -1.0;
            	}
    			X[line - 1][0] = 1.0;
    			line++;
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
	
	public static void main(String[] args) throws IOException {
		/**
		// TODO Auto-generated method stub
		double[] y = new double[210];
		double[][] X = new double[210][20];
		
		SVMII svm = new SVMII(0.0001);
		String trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_all.data";
		loadData(X,y,trainFile, "BRICKFACE");
		svm.Train(X,y,7000);
		
		SVMII svm1 = new SVMII(0.0001);
		trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_6.data";
		loadData(X,y,trainFile, "SKY");
		svm1.Train(X,y,7000);
		
		SVMII svm2 = new SVMII(0.0001);
		trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_5.data";
		loadData(X,y,trainFile, "FOLIAGE");
		svm2.Train(X,y,7000);
		
		SVMII svm3 = new SVMII(0.0001);
		trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_4.data";
		loadData(X,y,trainFile, "CEMENT");
		svm3.Train(X,y,7000);
		
		SVMII svm4 = new SVMII(0.0001);
		trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_3.data";
		loadData(X,y,trainFile, "WINDOW");
		svm4.Train(X,y,7000);
		
		SVMII svm5 = new SVMII(0.0001);
		trainFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\train_2.data";
		loadData(X,y,trainFile, "PATH");
		svm5.Train(X,y,7000);
		
		double[] test_y = new double[8];
		double[][] test_X = new double[8][20];
		double[] test_y6 = new double[8];
		double[][] test_X6 = new double[8][20];
		double[] test_y5 = new double[8];
		double[][] test_X5 = new double[8][20];
		double[] test_y4 = new double[8];
		double[][] test_X4 = new double[8][20];
		double[] test_y3 = new double[8];
		double[][] test_X3 = new double[8][20];
		double[] test_y2 = new double[8];
		double[][] test_X2 = new double[8][20];
		
		String testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_test.data";
		loadData(test_X,test_y,testFile, "BRICKFACE");
		testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_test.data";
		loadData(test_X6,test_y6,testFile, "SKY");
		testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_test.data";
		loadData(test_X5,test_y5,testFile, "FOLIAGE");
		testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_test.data";
		loadData(test_X4,test_y4,testFile, "CEMENT");
		testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_test.data";
		loadData(test_X3,test_y3,testFile, "WINDOW");
		testFile = "E:\\作业\\数据仓储\\2\\simpleSvm-master\\simpleSvm-master\\test_test.data";
		loadData(test_X2,test_y2,testFile, "PATH");
		
		//svm.Test(test_X, test_y);
		
		
		int error = 0;
		for(int i = 0; i < test_X.length; i++) {
			if (svm.predict(test_X[i]) != test_y[i]) {
				error++;
			}
			else if (svm.predict(test_X[i]) == -1) {
				if (svm1.predict(test_X6[i]) != test_y6[i]) {
					error++;
				}
				else if (svm1.predict(test_X6[i]) == -1) {
					if (svm2.predict(test_X5[i]) != test_y5[i]) {
						error++;
					}
					else  if (svm2.predict(test_X5[i]) == -1){
						if (svm3.predict(test_X4[i]) != test_y4[i]) {
							error++;
						}
						else if (svm3.predict(test_X4[i]) == -1) {
							if (svm4.predict(test_X3[i]) != test_y3[i]) {
								error++;
							}
							else if (svm4.predict(test_X3[i]) == -1) {
								if (svm5.predict(test_X2[i]) != test_y2[i]) {
									error++;
								}
							}
						}
					}
				}
			}
		}
		System.out.println("total:"+test_X.length);
		System.out.println("error:"+error);
		System.out.println("error rate:"+((double)error/test_X.length));
		System.out.println("acc rate:"+((double)(test_X.length-error)/test_X.length));
	*/	
	}
}

