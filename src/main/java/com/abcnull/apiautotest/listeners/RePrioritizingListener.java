package com.abcnull.apiautotest.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * RePrioritizingListener
 *
 * @author lei.shi06@hand-china.com
 * @version 1.0.0
 * @date 2019/12/26
 */
public class RePrioritizingListener implements IAnnotationTransformer {
    HashMap<Object, Integer> priorityMap = new HashMap<Object, Integer>();
    Integer class_priorityCounter = 10000;
    // The length of the final priority assigned to each method.
    Integer max_testpriorityLength = 4;

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        // class of the test method.
        Class<?> declaringClass = method.getDeclaringClass();
        // Current priority of the test assigned at the test method.
        Integer test_priority = iTestAnnotation.getPriority();
        // Current class priority.
        Integer current_ClassPriority = priorityMap.get(declaringClass);

        if (current_ClassPriority == null) {
            current_ClassPriority = class_priorityCounter++;
            priorityMap.put(declaringClass, current_ClassPriority);
        }

        String concatenatedPriority = test_priority.toString();

        // Adds 0's to start of this number.
        while (concatenatedPriority.length() < max_testpriorityLength) {
            concatenatedPriority = "0" + concatenatedPriority;
        }

        // Concatenates our class counter to the test level priority (example
        // for test with a priority of 1: 1000100001; same test class with a
        // priority of 2: 1000100002; next class with a priority of 1. 1000200001)
        concatenatedPriority = current_ClassPriority.toString() + concatenatedPriority;

        //Sets the new priority to the test method.
        iTestAnnotation.setPriority(Integer.parseInt(concatenatedPriority));

        String printText = method.getName() + " Priority = " + concatenatedPriority;
        Reporter.log(printText);
        System.out.println(printText);
    }
}
