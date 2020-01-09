package Huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class HuffmanNode {

    int data;
    char c;
    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {

    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.data - y.data;
    }
}

public class Huffman {

    static Character[] char_arr;
    static String Output;
    static String destination;
    static Integer[] freq;
    static String readstring;
    static String FILE;
    static int count = 0;
    static HashMap<Character, String> char_freq;
    static HashMap<Character, String> table;
    static String returnValue = "";
    static String code = "";
    static StringBuilder sb5;

    public static void printCode(HuffmanNode root, String s) {                  //recursive function 
        String ss = "";
        ss += root.c;
        if (root.left == null && root.right == null ) {                          //lw left w right b null
            if((ss.equals(" ") && ss.length() < 2) || !ss.trim().isEmpty())
            char_freq.put(root.c, s);
            System.out.println("root : " + root.c);
            return;
        }
        StringBuilder sb = new StringBuilder(s);
        sb.append("0");
        StringBuilder sb2 = new StringBuilder(s);
        sb2.append("1");
        printCode(root.left, sb.toString());    // left add "0" 
        printCode(root.right, sb2.toString());   // right add "1" 
    }

    public static void main(String[] args) throws IOException {
        int compressionRatio = 0;
        Scanner s = new Scanner(System.in);
        System.out.println("Huffman Menu: \n");
        System.out.println("1. Compression \n");
        System.out.println("2. Decompression \n");

        String choice = s.nextLine();
        int number = Integer.parseInt(choice);

        if (number == 1) {
            System.out.println("\nPlease Enter Filename you want to compress (in this format example.txt): ");
            FILE = s.nextLine();
            System.out.println("\nYour Filename is : " + FILE + " and your choose Compression ");
            System.out.println("\nPlease Enter Filename You Want To Save Your Output In (in this format example.txt): ");
            Output = s.nextLine();
            System.out.println("\nYour Output will be saved in : " + Output);
            readFileAsString(FILE);
            System.out.println("2aret elfile");
//            System.out.println(readstring);
            getOccuringChar(readstring);
            System.out.println("gebt el frequency");
            System.out.println("char_arr : ");
            for (int x = 0; x < char_arr.length; x++) {

                System.out.println(char_arr[x]);

            }
            System.out.println("Frequency : ");

            for (int x = 0; x < freq.length; x++) {

                System.out.println(freq[x]);

            }

            int n = char_arr.length;
            int i = 0;
            PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new MyComparator());

            while (i < n) {

                HuffmanNode hn = new HuffmanNode();
                hn.c = char_arr[i];
                hn.data = freq[i];
                hn.left = null;
                hn.right = null;
                q.add(hn);
                i++;
            }

            HuffmanNode root = null;                    // initialize el root b null
            while (q.size() > 1) {                      // extract l7d ma el size yeb2a 1
                HuffmanNode x = q.peek();               // extract el 2ola
                q.poll();
                HuffmanNode y = q.peek();               // extract el tania
                q.poll();
                HuffmanNode f = new HuffmanNode();      // El magmo3
                f.data = x.data + y.data;
                f.c = '-';
                f.left = x;                             // left
                f.right = y;                            // right
                root = f;                               // De el root
                q.add(f);                               // 7ot fe el queue
            }
            char_freq = new HashMap<Character, String>(freq.length);
            long startTime = System.currentTimeMillis();
            printCode(root, "");
            writecodetable(char_freq, Output);
            System.out.println(readstring.length());
            System.out.println(sb5.length()-1);
            System.out.println(sb5);
            compressionRatio = (int)(((readstring.length()*8.0)/(sb5.length()-1)/2)*100);
            System.out.println("Compression Ratio equal: " +  compressionRatio + "%");
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Execution time in milliseconds: " + elapsedTime);

        } else if (number == 2) {
            System.out.println("\nPlease Enter Filename You Want To Decompress (in this format example.txt): ");
            FILE = s.nextLine();
            System.out.println("\nYour Filename is : " + FILE + " and your choose Decompression ");
            System.out.println("\nPlease Enter Filename You Want To Save Decompressed File (in this format example.txt): ");
            destination = s.nextLine();
            long startTime = System.currentTimeMillis();
            decompress(FILE, destination);
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Execution time in milliseconds: " + elapsedTime);
        }

    }

    public static void readFileAsString(String fileName) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
            text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
            readstring = text.trim();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    static void getOccuringChar(String str) {
        HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();
        char[] strArray = str.toCharArray();

        for (char c : strArray) {
            if (charCountMap.containsKey(c)) {
                charCountMap.put(c, charCountMap.get(c) + 1);
            } else {
                charCountMap.put(c, 1);
            }
        }

        Set<Character> keys = charCountMap.keySet();
        Character[] k = keys.toArray(new Character[0]);
        char_arr = k;

        Collection<Integer> values = charCountMap.values();
        values.toArray();
        Integer[] foos = values.toArray(new Integer[0]);
        freq = foos;

        System.out.println(str + " : " + charCountMap);
    }

    public static String removeDupsInPlace(String word) {
        final StringBuilder output = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            String character = word.substring(i, i + 1);
            if (output.indexOf(character) < 0) // if not contained
            {
                output.append(character);
            }
        }
        return output.toString();
    }

    public static void writecodetable(HashMap<Character, String> hm, String Prototype) {
        try {
            FileWriter fw = new FileWriter(Prototype, true);
            for (Map.Entry mapelement : hm.entrySet()) {
                StringBuilder sb4 = new StringBuilder(mapelement.getKey().toString());
                sb4.append("=");
                sb4.append(mapelement.getValue());
                sb4.append("\n");
                System.out.println("sb4 : " + sb4.toString());
                fw.write(sb4.toString());
            }
            System.out.println(hm);

            sb5 = new StringBuilder(code);

            for (int u = 0; u < readstring.length(); u++) {
                if (char_freq.containsKey(readstring.charAt(u))) {
                    sb5.append(char_freq.get(readstring.charAt(u)));
                    //code = code + char_freq.get(readstring.charAt(u));
                }
            }

            //sb5 = sb5.append("\n");
            String test = sb5.toString() + "\n";
            sb5.append(test);
            //code = System.lineSeparator() + code;

            fw.append(sb5.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Success...");
    }

    public static void decompress(String Input, String Prototype) {
 BufferedReader reader;
        char letter;
        String serial;
        int counter = 0;
        int uu = 0;
        String temp = "";
        int step = 0;
        try {
            reader = new BufferedReader(new FileReader(Input));
            while(reader.readLine()!=null){
                counter++;
            }
            reader = new BufferedReader(new FileReader(Input));
            counter = counter - 1 ;
            table = new HashMap<Character, String>(counter);
                while(uu<counter){
                String line = reader.readLine();
                    //System.out.println("new line is " + line);
                
                String[] arrOfStr2 = line.split("=");
                    //System.out.println("after splitting : " + arrOfStr2[0]);
                    letter = arrOfStr2[0].charAt(0);
                    serial = arrOfStr2[1];
                    table.put(letter, serial);
                    uu++;
                }
                String line2 = reader.readLine();
                //System.out.println("second line is : " + line2);
                //System.out.println("line length : " + line2.length());
                StringBuilder sb8 = new StringBuilder(temp);
                StringBuilder sb9 = new StringBuilder(returnValue);
                while (step < line2.length()/2) {
                    sb8.append(line2.charAt(step));
                    //temp = temp + line2.charAt(step);
                    for (Map.Entry mapelement : table.entrySet()) {
                        if (mapelement.getValue().equals(sb8.toString())) {
                            sb9.append(mapelement.getKey().toString());
                            //returnValue = returnValue + mapelement.getKey();
                            //System.out.println("return value : " + returnValue);
                            sb8.replace(0,sb8.toString().length(),"");
                        }
                    }
                    step++;
                }
                System.out.println("step is : " + step);
            
            FileWriter fw = new FileWriter(Prototype);
            fw.write(sb9.toString());
            reader.close();
            fw.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}
