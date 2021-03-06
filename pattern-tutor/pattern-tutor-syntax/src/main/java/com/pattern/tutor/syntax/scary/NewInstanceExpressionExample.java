package com.pattern.tutor.syntax.scary;

import java.lang.reflect.Constructor;

import com.pattern.tutor.syntax.scary.util.CloneUtils;

public class NewInstanceExpressionExample {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// #1
		NewInstanceExpression instance = new NewInstanceExpression("Spark", 23);
		System.out.printf("Class name is %s, toString is %s.\n", instance.getClass().getName(), instance);
		
		// #2
		try {
			Class<NewInstanceExpression> cls = (Class<NewInstanceExpression>) Class.forName("com.jangz.syntax.scary.NewInstanceExpression");
			NewInstanceExpression instance2 = cls.newInstance();
			instance2.setName("Spark");
			instance2.setAge(23);
			System.out.printf("Class name is %s, toString is %s.\n", instance2.getClass().getName(), instance2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// #3
		try {
			Class<NewInstanceExpression> cls = (Class<NewInstanceExpression>) Class.forName("com.jangz.syntax.scary.NewInstanceExpression");
			Constructor<NewInstanceExpression> constructor = cls.getConstructor(String.class, int.class);
			NewInstanceExpression instance3 = constructor.newInstance("Spark", 23);
			System.out.printf("Class name is %s, toString is %s.\n", instance3.getClass().getName(), instance3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// #4
		try {
			NewInstanceExpression instanceFromClone = (NewInstanceExpression) instance.clone();
			System.out.printf("Class name is %s, toString is %s.\n", instanceFromClone.getClass().getName(), instanceFromClone);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		// #5
		NewInstanceExpression instanceFromSerialize = CloneUtils.clone(instance);
		System.out.printf("Class name is %s, toString is %s.\n", instanceFromSerialize.getClass().getName(), instanceFromSerialize);
	}
}
