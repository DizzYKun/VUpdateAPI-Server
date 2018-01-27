/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcml.space.Utils;

import java.net.Socket;
import java.util.*;

/**
 *
 * @author Administrator
 */
public class Utils {
    
    public static void StringToUpload(String message,Socket socket){
        int projectnamebetweenversion = message.indexOf("-");
        int versionbetweendownload = message.indexOf("-", projectnamebetweenversion + 1);
        int downloadbetweenPW = message.indexOf("-", versionbetweendownload + 1);
        int PWBetweenQQ = message.indexOf("-", downloadbetweenPW + 1);
        String projectname = message.substring(0, projectnamebetweenversion);
        String versionstr = message.substring(projectnamebetweenversion+1, versionbetweendownload);
        String download = message.substring(versionbetweendownload+1, downloadbetweenPW);
        String PW = message.substring(downloadbetweenPW+1, PWBetweenQQ);
        String QQ = message.substring(PWBetweenQQ+1,message.length());
        projectname = projectname.replaceAll("¡ì", "-");
        download = download.replaceAll("¡ì", "-");
        PW = PW.replaceAll("¡ì", "-");
        DataUtils.uploadData(projectname, Integer.parseInt(versionstr), download, PW, Integer.parseInt(QQ),socket);
    }

    public static void RemoveANumber(int num, List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == num) {
                numbers.remove(i);
            }
        }
    }

    public static int getRandom(int less, int most) {
        java.util.Random random = new java.util.Random();
        int result = random.nextInt(most);
        return result + less;
    }

    public static int getRandomNumberCardWithoutHave(List<Integer> selfnumber, List<Integer> othernumber) {
        java.util.Random random = new java.util.Random();
        List<Integer> AllNumbers = new ArrayList();
        AllNumbers.addAll(selfnumber);
        AllNumbers.addAll(othernumber);
        int result = random.nextInt(11) + 1;
        if (AllNumbers.contains(result)) {
            for (int nowtestnum = 1; nowtestnum <= 11; nowtestnum++) {
                if (AllNumbers.contains(nowtestnum)) {
                    if (nowtestnum == 11) {
                        return -1;
                    }
                    continue;
                } else {
                    return Utils.getRandomNumberCardWithoutHave(selfnumber, othernumber);
                }
            }
        }
        return result;
    }

    public static int getNumberAddResult(List<Integer> list) {
        int result = 0;
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            result = result + it.next();
        }
        return result;
    }

    public static int getMax(List<Integer> list) {
        return Collections.max(list);
    }

    public static List<String> IntegerListToStringList(List<Integer> IntegerList) {
        ArrayList<String> strings = new ArrayList();
        int sls = IntegerList.size();
        for (int i = 0; i < sls; i++) {
            strings.add(IntegerList.get(i).toString());
        }
        return strings;
    }

    public static List<Integer> StringListToIntegerList(List<String> StringsList) {
        ArrayList<Integer> numbers = new ArrayList();
        int sls = StringsList.size();
        for (int i = 0; i < sls; i++) {
            numbers.add(Integer.parseInt(StringsList.get(i)));
        }
        return numbers;
    }

    public static String ListToString(List<String> list) {
        return list.toString();
    }

    public static List<String> StringToList(String toString) {
        List<String> toList = Arrays.asList(toString.substring(1, toString.length() - 1).split(", "));
        if (toList.size() == 1 && toList.get(0).equals("")) {
            return new ArrayList();
        } else {
            return toList;
        }
    }

    public static int getMin(List<Integer> ins) {
        Object[] objs = ins.toArray();
        Arrays.sort(objs);
        return Integer.valueOf(String.valueOf(objs[0]));
    }
}
