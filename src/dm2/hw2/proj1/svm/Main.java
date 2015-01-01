package dm2.hw2.proj1.svm;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int trainset_size = 210;
		int attr_num = 20;
		int testset_size = 2100;
		
		double[] y = new double[trainset_size];
		double[][] X = new double[trainset_size][attr_num];
		double[] y6 = new double[trainset_size - 30*1];
		double[][] X6 = new double[trainset_size - 30*1][attr_num];
		double[] y5 = new double[trainset_size - 30*2];
		double[][] X5 = new double[trainset_size - 30*2][attr_num];
		double[] y4 = new double[trainset_size - 30*3];
		double[][] X4 = new double[trainset_size - 30*3][attr_num];
		double[] y3 = new double[trainset_size - 30*4];
		double[][] X3 = new double[trainset_size - 30*4][attr_num];
		double[] y2 = new double[trainset_size - 30*5];
		double[][] X2 = new double[trainset_size - 30*5][attr_num];
		
		SVMII svm = new SVMII(0.0001);
		String trainFile = "/Data/train_all.data";
		svm.loadData(X,y,trainFile, "BRICKFACE");
		svm.Train(X,y,7000);
		
		SVMII svm1 = new SVMII(0.0001);
		trainFile = "/Data/train_6.data";
		svm.loadData(X6,y6,trainFile, "SKY");
		svm1.Train(X6,y6,7000);
		
		SVMII svm2 = new SVMII(0.0001);
		trainFile = "/Data/train_5.data";
		svm.loadData(X5,y5,trainFile, "FOLIAGE");
		svm2.Train(X5,y5,7000);
		
		SVMII svm3 = new SVMII(0.0001);
		trainFile = "/Data/train_4.data";
		svm3.loadData(X4,y4,trainFile, "CEMENT");
		svm3.Train(X4,y4,7000);
		
		SVMII svm4 = new SVMII(0.0001);
		trainFile = "/Data/train_3.data";
		svm4.loadData(X3,y3,trainFile, "WINDOW");
		svm4.Train(X3,y3,7000);
		
		SVMII svm5 = new SVMII(0.0001);
		trainFile = "/Data/train_2.data";
		svm5.loadData(X2,y2,trainFile, "PATH");
		svm5.Train(X2,y2,7000);
		
		double[] test_y = new double[testset_size];
		double[][] test_X = new double[testset_size][attr_num];
		double[] test_y6 = new double[testset_size];
		double[][] test_X6 = new double[testset_size][attr_num];
		double[] test_y5 = new double[testset_size];
		double[][] test_X5 = new double[testset_size][attr_num];
		double[] test_y4 = new double[testset_size];
		double[][] test_X4 = new double[testset_size][attr_num];
		double[] test_y3 = new double[testset_size];
		double[][] test_X3 = new double[testset_size][attr_num];
		double[] test_y2 = new double[testset_size];
		double[][] test_X2 = new double[testset_size][attr_num];
		
		
		String testFile = "/Data/test_all.data";
		svm.loadData(test_X,test_y,testFile, "BRICKFACE");
		svm1.loadData(test_X6,test_y6,testFile, "SKY");
		svm3.loadData(test_X4,test_y4,testFile, "CEMENT");
		svm4.loadData(test_X3,test_y3,testFile, "WINDOW");
		svm5.loadData(test_X2,test_y2,testFile, "PATH");
		
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
		
	}
}
