package com.example.knapsack.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods implements Parcelable {
    /* 货品对象的属性值 */
    private int id; //货品编号
	private int weight; //货品重量
	private double value; //货品价值
    private double wvproportion; //货品价值重量比
    private String select;

    /* 构造器 */
	public Goods()
	{
	}

    public Goods(int id,int weight,double value,double wvproportion,String select)
    {
        this.id = id;
        this.weight = weight;
        this.value = value;
        this.wvproportion = wvproportion;
        this.select = select;
    }
    /* get和set函数*/

    protected Goods(Parcel in) {
        id = in.readInt();
        weight = in.readInt();
        value = in.readDouble();
        wvproportion = in.readDouble();
        select = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight()
   {
	  return weight;
   }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public void setWeight(int weight)
    {
    this.weight = weight;
    }

    public double getValue()
    {
	   return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWvproportion() {
        return wvproportion;
    }

    public void setWvproportion(double wvproportion) {
        this.wvproportion = wvproportion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(weight);
        parcel.writeDouble(value);
        parcel.writeDouble(wvproportion);
        parcel.writeString(select);
    }
}
