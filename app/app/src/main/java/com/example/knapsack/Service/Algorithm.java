package com.example.knapsack.Service;

import java.math.BigDecimal;

public class Algorithm {
	/*构造器*/
	public Algorithm()
	{	}

	//贪心算法
	public double[][] Greedy(double[][] x ,int w[],int v[],int n, int C)
	{
		double[] c = new double[1000];
		//计算每个物品单位重量价值比
		for(int i = 0;i < n;i++)
		{
			c[i] =(double)v[i]/(double)w[i];
		}
		//对每个物品按照单位重量价值比进行排序
		for(int i = 0;i < n;i++)
		{
			for(int j = i+1;j < n;j++)
			{
				if(c[i] <= c[j]) //c[i] <= c[j]，将对应物品进行交换
				{
					//交换物品单位重量价值比
					double temp = c[i];
					c[i] = c[j];
					c[j] = temp;
					//交换物品重量
					int tempw = w[i];
					w[i] = w[j];
					w[j] = tempw;
					//交换物品价值
					int tempv = v[i];
					v[i] = v[j];
					v[j] = tempv;

				}
			}
		}
		//初始化数组x
		for(int i = 0;i < n;i++)
		{
			x[i][0] = 0;
		}
		int maxValue= 0;//背包最大价值
		int i;
		for (i=0; w[i]<C; i++ )
		{
			x[i][0]=1;//装入当前物品
			maxValue+=v[i] ;//更新背包价值
			C=C-w[i] ;//更新背包容量
		}
		x[n][0] =maxValue ;
		return x;

	}



	double cw = 0.0;//当前背包重量
	double cp = 0.0;//当前背包中物品价值
	double bestp = 0.0;//当前最优价值
	double[] perp = new double[1000];//单位物品价值排序后
	double[] put= new double [1000];//设置是否装入

	//按单位价值排序
	void knapsack(int[] w,int[] v,int n)
	{
		int i,j;
		int temp = 0;

		for(i=0;i<n;i++)
			perp[i]=v[i]/w[i];
		for(i=0;i<n-1;i++)
		{
			for(j=i+1;j<n;j++)
				if(perp[i]<perp[j])//冒泡排序perp[],sortv[],sortw[]
				{
					double t = perp[i];
					perp[i]=perp[i];
					perp[j]=t;

					temp = v[i];
					v[i]=v[j];
					v[j]=temp;

					temp=w[i];
					w[i]=w[j];
					w[j]=temp;
				}
		}
	}

	//回溯函数
	void backtrack(int i,int[] w,int[] v,int C,int n)
	{
		bound(i,w,v,n,C); //计算上界
		if(i>=n)  //所有元素都已遍历到，回溯结束
		{
			bestp = cp;
			return ;
		}
		if(cw+w[i]<=C)
		{
			//放入背包
			cw+=w[i];
			cp+=v[i];
			put[i]=1;
			backtrack(i+1,w,v,C,n);
			cw-=w[i];
			cp-=v[i];
		}
		if(bound(i+1,w,v,n,C)>bestp)//符合条件搜索右子数
			backtrack(i+1,w,v,C,n);
	}


	//计算上界函数
	double bound(int i ,int[] w,int[] v,int n,int C)
	{
		double leftw= C-cw;
		double b = cp;
		while(i<n && w[i]<=leftw)
		{
			leftw-=w[i];
			b+=v[i];
			i++;
		}
		if(i<n)
			b+=v[i]/w[i]*leftw;
		return b;//返回上界值
	}
	double[] Backtracking(int[] w,int[] v,int n,int C)
	{
		knapsack(w,v,n);
		backtrack(0,w,v,C,n);
		put[n] = bestp;
		return put;
	}







	//动态规划算法
	public int[] Dynamic(int w[ ], int v[ ],int n,int C)
	{
		int i,j;
		int[] x = new int [10000];
		int[][] V = new int [10000][10000];
		for (i=0; i<=n; i++)   //初始化第0列
		{
			V[i][0]=0;
		}
		for (j=0; j<=C; j++)   //初始化第0行
		{
			V[0][j]=0;
		}
		for (i=1; i<=n; i++)   //计算第i行，进行第i次迭代
		{
			for (j=1; j<=C; j++)
			{
				if (j<w[i])
				{
					V[i][j]=V[i-1][j];
				}
				else
				{
					if(V[i-1][j] > V[i-1][j-w[i]]+v[i])
					{
						V[i][j]=V[i-1][j];
					}
					else
					{
						V[i][j]=V[i-1][j-w[i]]+v[i];
					}
				}

			}
		}
		j=C;    //求装入背包的物品
		for (i=n; i>0; i--)
		{
			if (V[i][j]>V[i-1][j])
			{
				x[i]=1;
				j=j-w[i];
			}
			else
			{
				x[i]=0;
			}
		}
		x[n] = V[n][C];//V[n][C]----背包取得的最大价值
		return x;

	}



	public double[][] Selectsort(double r[][], int first, int end){
		//插入排序
		for (int i = 1; i < end; i++) {
			//外层循环，从第二个开始比较
			for (int j = i; j > 0; j--) {
				//内存循环，与前面排好序的数据比较，如果后面的数据小于前面的则交换
				if (r[j][0] < r[j - 1][0]) {
					double[] temp = new double[100];
					temp = r[j - 1];
					r[j - 1] = r[j];
					r[j] = temp;
				} else {
					//如果不小于，说明插入完毕，退出内层循环
					break;
				}
			}
		}
		return r;
	}

}


/*
//回溯算法
	public int[] Backtracking(int w[], int v[], int n,int C)
	{
	   int cw=0, cv=0,bestv=0,k=0;
	   int[] x = new int [1000];
	   int[] bestx = new int [1000];
       for (int i = 0; i < n ;i++)
			   x[i]=2;
			while (k>=0)
			{
				x[k]=x[k]-1;
			   while (x[k]>=0)
			   {
			    if (cw+w[k]<=C)
			    {
			    	if (bound(k+1,cw,cv,n,C,w,v) > bestv)
			    	{
			    		cv=cv+v[k]*x[k];
			    		cw=cw+w[k]*x[k];
			    		break;
			    	}
			    	else
			    	{
			    		x[k]=x[k]-1;
			    	}
			   }
			  else
			  {
				  x[k]=x[k]-1;
			  }
			 }
			if (x[k]>=0 && k==n) { //得到一种装法
				if (cv > bestv)
				{
					bestv=cv;
					for( int i=1;i<=n;i++)
						bestx[i]=x[i];
				}
			if (x[k]>=0 && k<n)
				k=k+1;
			//安排下一个物品
			else {
			cw=cw-w[k]*x[k];
			cv=cv-v[k]*x[k];
			x[k]=2;
			//重置x[k],回溯
			k=k-1;
			}
		}
	  }
			for(int j1 = 0;j1 <= n;j1++)
	 		{
	 			 System.out.println(x[j1]+"i   " );

	 		}

	 return x;

	}
	//计算上界函数
	double bound(int i,int cw,int cv,int n, int C,int w[],int v[])
	{
	    double leftw= C-cw;
	    double b = cv;
	    while(i<=n && w[i]<=leftw)
	    {
	        leftw-=w[i];
	        b+=v[i];
	        i++;
	    }
	    if(i<=n)
	        b+=v[i]/w[i]*leftw;
	    return b;

	}



 	//快速排序
	int Partition(double r[][], int first, int end)//划分
	{
		int i = first, j = end;
		//初始化待划分区间
		while (i < j)	{
			while (i < j && compare(r[i][0] ,r[j][0]) <= 0) j--;
			//右侧扫描
			if (i < j)
			{
				//将较小记录交换到前面

				 System.out.println(r[i][1]+"i" );
   			     System.out.println(r[i][0]+"i"  );
				 System.out.println(r[j][1]+"j" );
   			     System.out.println(r[j][0]+"j"  );
				double[] temp = new double[100];

				temp = r[i]; r[i] = r[j]; r[j] = temp;
				 System.out.println(r[i][1]+"i" );
   			     System.out.println(r[i][0]+"i"  );
				 System.out.println(r[j][1]+"j" );
   			     System.out.println(r[j][0]+"j"  );

				i++;
			}
			while (i < j && compare(r[i][0] ,r[j][0]) <= 0) i++;
			//左侧扫描
			if (i < j)
			{
				//将较大记录交换到后面
				double[] temp = new double[100];
				temp = r[i]; r[i] = r[j]; r[j] = temp;


				j--;
			}
		}
		return i;												// 返回轴值记录的位置
	}


	public double[][] QuickSort(double r[][], int first, int end){
		//快速排序
		int pivot;
		for(int i = 0;i <= end;i++)
	{
		 System.out.print(r[i][1]+"i   " );
		     System.out.println(r[i][0]+"i"  );
	}
		 System.out.println("i                 "  );


		if (first < end) {
			pivot = Partition(r, first, end);
			//划分，pivot是轴值在序列中的位置
			r=QuickSort(r, first, pivot - 1);
			//求解子问题1，对左侧子序列进行快速排序
			r=QuickSort(r, pivot + 1, end);
			//求解子问题2，对右侧子序列进行快速排序
			for(int i = 0;i <= end;i++)
		{
			 System.out.print(r[i][1]+"i   " );
			     System.out.println(r[i][0]+"i"  );
		}
			 System.out.println("i                 "  );
		}

      return r;
	}

	//比较大小
	int compare(double d1,double d2)
	{
		BigDecimal d11 = new BigDecimal(d1);
		BigDecimal d22 = new BigDecimal(d2);
		int res =  d11.compareTo(d22); 
	    return res;
	}
	
 
*/