package com.kenneth.boot;

import java.util.Arrays;

import org.junit.Test;

import com.github.jsonzou.jmockdata.JMockData;

import lombok.Data;

public class JmockdataTest {
	
	@Test
	public void test1(){
		TestMock testMock = JMockData.mock(TestMock.class);
		System.out.println(testMock.toString());
	}
}

@Data
class TestMock{
	  //基本类型
	  private byte byteNum;
	  private boolean booleanNum;
	  private char charNum;
	  private short shortNum;
	  private int integerNum;
	  private long longNum;
	  private float floatNum;
	  private double doubleNum;
	  //基本包装类型
	  private Byte byteBoxing;
	  private Boolean booleanBoxing;
	  private Character charBoxing;
	  private Short shortBoxing;
	  private Integer integerBoxing;
	  private Long longBoxing;
	  private Float floatBoxing;
	  private Double doubleBoxing;
	  //基本类型数组
	  private byte[] byteNumArray;
	  private boolean[] booleanNumArray;
	  private char[] charNumArray;
	  private short[] shortNumArray;
	  private int[] integerNumArray;
	  private long[] longNumArray;
	  private float[] floatNumArray;
	  private double[] doubleNumArray;
	public byte getByteNum() {
		return byteNum;
	}
	public void setByteNum(byte byteNum) {
		this.byteNum = byteNum;
	}
	public boolean isBooleanNum() {
		return booleanNum;
	}
	public void setBooleanNum(boolean booleanNum) {
		this.booleanNum = booleanNum;
	}
	public char getCharNum() {
		return charNum;
	}
	public void setCharNum(char charNum) {
		this.charNum = charNum;
	}
	public short getShortNum() {
		return shortNum;
	}
	public void setShortNum(short shortNum) {
		this.shortNum = shortNum;
	}
	public int getIntegerNum() {
		return integerNum;
	}
	public void setIntegerNum(int integerNum) {
		this.integerNum = integerNum;
	}
	public long getLongNum() {
		return longNum;
	}
	public void setLongNum(long longNum) {
		this.longNum = longNum;
	}
	public float getFloatNum() {
		return floatNum;
	}
	public void setFloatNum(float floatNum) {
		this.floatNum = floatNum;
	}
	public double getDoubleNum() {
		return doubleNum;
	}
	public void setDoubleNum(double doubleNum) {
		this.doubleNum = doubleNum;
	}
	public Byte getByteBoxing() {
		return byteBoxing;
	}
	public void setByteBoxing(Byte byteBoxing) {
		this.byteBoxing = byteBoxing;
	}
	public Boolean getBooleanBoxing() {
		return booleanBoxing;
	}
	public void setBooleanBoxing(Boolean booleanBoxing) {
		this.booleanBoxing = booleanBoxing;
	}
	public Character getCharBoxing() {
		return charBoxing;
	}
	public void setCharBoxing(Character charBoxing) {
		this.charBoxing = charBoxing;
	}
	public Short getShortBoxing() {
		return shortBoxing;
	}
	public void setShortBoxing(Short shortBoxing) {
		this.shortBoxing = shortBoxing;
	}
	public Integer getIntegerBoxing() {
		return integerBoxing;
	}
	public void setIntegerBoxing(Integer integerBoxing) {
		this.integerBoxing = integerBoxing;
	}
	public Long getLongBoxing() {
		return longBoxing;
	}
	public void setLongBoxing(Long longBoxing) {
		this.longBoxing = longBoxing;
	}
	public Float getFloatBoxing() {
		return floatBoxing;
	}
	public void setFloatBoxing(Float floatBoxing) {
		this.floatBoxing = floatBoxing;
	}
	public Double getDoubleBoxing() {
		return doubleBoxing;
	}
	public void setDoubleBoxing(Double doubleBoxing) {
		this.doubleBoxing = doubleBoxing;
	}
	public byte[] getByteNumArray() {
		return byteNumArray;
	}
	public void setByteNumArray(byte[] byteNumArray) {
		this.byteNumArray = byteNumArray;
	}
	public boolean[] getBooleanNumArray() {
		return booleanNumArray;
	}
	public void setBooleanNumArray(boolean[] booleanNumArray) {
		this.booleanNumArray = booleanNumArray;
	}
	public char[] getCharNumArray() {
		return charNumArray;
	}
	public void setCharNumArray(char[] charNumArray) {
		this.charNumArray = charNumArray;
	}
	public short[] getShortNumArray() {
		return shortNumArray;
	}
	public void setShortNumArray(short[] shortNumArray) {
		this.shortNumArray = shortNumArray;
	}
	public int[] getIntegerNumArray() {
		return integerNumArray;
	}
	public void setIntegerNumArray(int[] integerNumArray) {
		this.integerNumArray = integerNumArray;
	}
	public long[] getLongNumArray() {
		return longNumArray;
	}
	public void setLongNumArray(long[] longNumArray) {
		this.longNumArray = longNumArray;
	}
	public float[] getFloatNumArray() {
		return floatNumArray;
	}
	public void setFloatNumArray(float[] floatNumArray) {
		this.floatNumArray = floatNumArray;
	}
	public double[] getDoubleNumArray() {
		return doubleNumArray;
	}
	public void setDoubleNumArray(double[] doubleNumArray) {
		this.doubleNumArray = doubleNumArray;
	}
	@Override
	public String toString() {
		return "TestMock [byteNum=" + byteNum + ", booleanNum=" + booleanNum + ", charNum=" + charNum + ", shortNum="
				+ shortNum + ", integerNum=" + integerNum + ", longNum=" + longNum + ", floatNum=" + floatNum
				+ ", doubleNum=" + doubleNum + ", byteBoxing=" + byteBoxing + ", booleanBoxing=" + booleanBoxing
				+ ", charBoxing=" + charBoxing + ", shortBoxing=" + shortBoxing + ", integerBoxing=" + integerBoxing
				+ ", longBoxing=" + longBoxing + ", floatBoxing=" + floatBoxing + ", doubleBoxing=" + doubleBoxing
				+ ", byteNumArray=" + Arrays.toString(byteNumArray) + ", booleanNumArray="
				+ Arrays.toString(booleanNumArray) + ", charNumArray=" + Arrays.toString(charNumArray)
				+ ", shortNumArray=" + Arrays.toString(shortNumArray) + ", integerNumArray="
				+ Arrays.toString(integerNumArray) + ", longNumArray=" + Arrays.toString(longNumArray)
				+ ", floatNumArray=" + Arrays.toString(floatNumArray) + ", doubleNumArray="
				+ Arrays.toString(doubleNumArray) + ", getByteNum()=" + getByteNum() + ", isBooleanNum()="
				+ isBooleanNum() + ", getCharNum()=" + getCharNum() + ", getShortNum()=" + getShortNum()
				+ ", getIntegerNum()=" + getIntegerNum() + ", getLongNum()=" + getLongNum() + ", getFloatNum()="
				+ getFloatNum() + ", getDoubleNum()=" + getDoubleNum() + ", getByteBoxing()=" + getByteBoxing()
				+ ", getBooleanBoxing()=" + getBooleanBoxing() + ", getCharBoxing()=" + getCharBoxing()
				+ ", getShortBoxing()=" + getShortBoxing() + ", getIntegerBoxing()=" + getIntegerBoxing()
				+ ", getLongBoxing()=" + getLongBoxing() + ", getFloatBoxing()=" + getFloatBoxing()
				+ ", getDoubleBoxing()=" + getDoubleBoxing() + ", getByteNumArray()="
				+ Arrays.toString(getByteNumArray()) + ", getBooleanNumArray()=" + Arrays.toString(getBooleanNumArray())
				+ ", getCharNumArray()=" + Arrays.toString(getCharNumArray()) + ", getShortNumArray()="
				+ Arrays.toString(getShortNumArray()) + ", getIntegerNumArray()="
				+ Arrays.toString(getIntegerNumArray()) + ", getLongNumArray()=" + Arrays.toString(getLongNumArray())
				+ ", getFloatNumArray()=" + Arrays.toString(getFloatNumArray()) + ", getDoubleNumArray()="
				+ Arrays.toString(getDoubleNumArray()) + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	 
	  
	
	  
}